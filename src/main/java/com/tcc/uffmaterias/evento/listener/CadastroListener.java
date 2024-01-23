package com.tcc.uffmaterias.evento.listener;

import com.tcc.uffmaterias.domain.service.SmtpEmailService;
import com.tcc.uffmaterias.dto.request.Menssagem;
import com.tcc.uffmaterias.error.erros.EmailException;
import com.tcc.uffmaterias.evento.event.CadastroEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CadastroListener implements ApplicationListener<CadastroEvent> {

    @Autowired
    private SmtpEmailService smtpEmailService;
    @Override
    public void onApplicationEvent(CadastroEvent event) {
        try {
            Menssagem menssagem = Menssagem.builder()
                    .assunto("Código de verificação")
                    .destinatario(event.getEmail())
                    .corpo("envio-user-register-code.html")
                    .variaveis(Map.of("code",event.getCode(),"email", event.getEmail()))
                    .build();
            smtpEmailService.enviar(menssagem);
        }catch (Exception e){
            throw new EmailException(e.getMessage(),e);
        }
    }
}
