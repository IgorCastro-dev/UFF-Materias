package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.redis.UserRegisterCode;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterCodeRepository;
import com.tcc.uffmaterias.dto.request.Menssagem;
import com.tcc.uffmaterias.error.erros.EmailException;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
public class RegisterCodeService {

    @Value("${webservice.igor.redis.recoverycode.timeout}")
    private String time;

    @Autowired
    private UserRegisterCodeRepository userRegisterCodeRepository;

    @Autowired
    private SmtpEmailService smtpEmailService;

    @Transactional
    public void sendRegisterCode(String email){
        String code = String.format("%04d",new Random().nextInt(10000));
        UserRegisterCode userRegisterCode = new UserRegisterCode();
        var userRegisterCodeOpitional = userRegisterCodeRepository.findByEmail(email);
        if (userRegisterCodeOpitional.isEmpty()){
            userRegisterCode.setEmail(email);
        }else {
            userRegisterCode = userRegisterCodeOpitional.get();
        }
        userRegisterCode.setCode(code);
        userRegisterCode.setDateTime(LocalDateTime.now());
        userRegisterCodeRepository.save(userRegisterCode);
        aoEnviarCodigo(userRegisterCode.getCode(),userRegisterCode.getEmail());
        System.out.println(code);
    }

    public Boolean isValidCode(String recoveryCode, String email) {
        UserRegisterCode userRegisterCode = userRegisterCodeRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Código não encontrado")
        );
        LocalDateTime timeout = userRegisterCode.getDateTime().plusMinutes(Long.parseLong(time));
        LocalDateTime now = LocalDateTime.now();

        return recoveryCode.equals(userRegisterCode.getCode()) && now.isBefore(timeout);
    }

    private void aoEnviarCodigo(String code,String email){
        try {
            Menssagem menssagem = Menssagem.builder()
                    .assunto("Código de verificação")
                    .destinatario(email)
                    .corpo("envio-user-register-code.html")
                    .variaveis(Map.of("code",code,"email",email))
                    .build();
            smtpEmailService.enviar(menssagem);
        }catch (Exception e){
            throw new EmailException(e.getMessage(),e);
        }
    }
}
