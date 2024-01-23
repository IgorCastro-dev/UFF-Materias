package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.dto.request.Menssagem;
import com.tcc.uffmaterias.error.erros.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class SmtpEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("${login.email.remetente}")
    private String remetente;

    public void enviar(Menssagem menssagem) {
        try {
            Template template = freemarkerConfig.getTemplate(menssagem.getCorpo());
            String corpo = FreeMarkerTemplateUtils.processTemplateIntoString(template,menssagem.getVariaveis());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(remetente);
            helper.setTo(menssagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(menssagem.getAssunto());
            helper.setText(corpo,true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new EmailException(e.getMessage(),e);
        }
    }

}
