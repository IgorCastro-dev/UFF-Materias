package com.tcc.uffmaterias.domain.controller;


import com.tcc.uffmaterias.domain.service.ConteudoSecaoService;
import com.tcc.uffmaterias.dto.request.ConteudoSecaoRequestDto;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conteudo")
@Tag(name = "Conteúdo", description = "Gerencia os conteúdos do tópico")
public class ConteudoSecaoController {

    @Autowired
    private ConteudoSecaoService conteudoSecaoService;

    @Operation(summary = "Faz o upload e salva o conteúdo passando o id do tópico no parâmetro")
    @PostMapping("/{topicoId}")
    public ResponseEntity<ConteudoSecaoResponseDto> uploadConteudo(
            @PathVariable("topicoId") Long secaoMateriaId,
            @Valid ConteudoSecaoRequestDto conteudoSecaoRequestDto){
        return ResponseEntity.ok(conteudoSecaoService.uploadConteudoSecao(secaoMateriaId,conteudoSecaoRequestDto));
    }

    @Operation(summary = "Atualiza o conteúdo passando o id do conteudo no parâmetro")
    @PutMapping("/{conteudoId}")
    public ResponseEntity<ConteudoSecaoResponseDto> atualizaConteudo(
            @PathVariable("conteudoId") Long conteudoId,
            @RequestPart(value = "arquivo",required = false) MultipartFile arquivo,
            @RequestPart("descricao") String descricao){
        return ResponseEntity.ok(conteudoSecaoService.atualizaConteudoSecao(conteudoId, arquivo, descricao));
    }

    @Operation(summary = "Lista os conteúdos do tópico passando o id do tópico no parâmetro")
    @GetMapping("/{topicoId}")
    public ResponseEntity<List<ConteudoSecaoResponseDto>> listarConteudo(
            @PathVariable("topicoId") Long secaoMateriaId){
        return ResponseEntity.ok(conteudoSecaoService.listarConteudos(secaoMateriaId));
    }

    @Operation(summary = "Buscar o conteúdo do tópico passando o id do conteúdo no parâmetro")
    @GetMapping("buscar/{conteudoId}")
    public ResponseEntity<ConteudoSecaoResponseDto> buscarConteudo(
            @PathVariable("conteudoId") Long conteudoId){
        return ResponseEntity.ok(conteudoSecaoService.buscarConteudo(conteudoId));
    }

    @Operation(summary = "Faz dowload do conteúdo passando o nome do conteudo no parâmetro")
    @GetMapping("/dowload/{fileNome}")
    public ResponseEntity<byte[]> dowloadConteudo(@PathVariable("fileNome") String fileNome) throws UnsupportedEncodingException {
        byte[] fileBytes = conteudoSecaoService.dowloadConteudo(fileNome);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String encodedFileName = URLEncoder.encode(fileNome, "UTF-8");
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(encodedFileName)
                .build();

        headers.setContentDisposition(contentDisposition);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}
