package com.tcc.uffmaterias.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroTemplate> notFoundException(NotFoundException n){
        ErroTemplate erro = ErroTemplate.builder()
                .detail(n.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, status, request,ex.getBindingResult());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroTemplate> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String detail = Objects.requireNonNull(ex.getRootCause()).getMessage();
        ErroTemplate erro = createErroBuilder(HttpStatus.BAD_REQUEST,ErroType.DADOS_INVALIDDOS,detail).timestamp(OffsetDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }


    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpStatusCode status, WebRequest request, BindingResult bindingResult) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto";
        List<ErroTemplate.Field> erroField = bindingResult.getFieldErrors().stream()
                .map(fieldError ->{
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return ErroTemplate.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());
        ErroTemplate erro = createErroBuilder((HttpStatus) status,ErroType.DADOS_INVALIDDOS,detail).timestamp(OffsetDateTime.now()).fields(erroField).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }




    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var detail = "O corpo da requisição esta inválido, verifique a sintaxe";
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException invalidformatexception){
            return handleInvalidFormException(invalidformatexception,new HttpHeaders(),status,request);
        } else if (rootCause instanceof PropertyBindingException propertyBindingException) {
            return handlePropertyBindingException(propertyBindingException,new HttpHeaders(),status,request);

        }
        ErroTemplate erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }


    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders httpHeaders, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format("A propriedade %s não pertence ao corpo da requisição",path);
        ErroTemplate erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    private ResponseEntity<Object> handleInvalidFormException(InvalidFormatException ex, HttpHeaders httpHeaders, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ErroTemplate erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    private ErroTemplate.ErroTemplateBuilder createErroBuilder(HttpStatus status, ErroType erroType,String detail){
        return ErroTemplate.builder()
                .timestamp(OffsetDateTime.now())
                .httpStatusCode(status.value())
                .httpStatus(status)
                .title(erroType.getTitle())
                .detail(detail);
    }
}
