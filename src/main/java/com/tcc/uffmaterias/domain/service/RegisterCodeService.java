package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.redis.UserRegisterCode;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterCodeRepository;
import com.tcc.uffmaterias.dto.request.Menssagem;
import com.tcc.uffmaterias.error.erros.EmailException;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import com.tcc.uffmaterias.evento.publisher.CadastroPublisher;
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
    private CadastroPublisher cadastroPublisher;

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
        cadastroPublisher.publicarEvento(email,code);
    }

    public Boolean isValidCode(String recoveryCode, String email) {
        UserRegisterCode userRegisterCode = userRegisterCodeRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Código não encontrado")
        );
        LocalDateTime timeout = userRegisterCode.getDateTime().plusMinutes(Long.parseLong(time));
        LocalDateTime now = LocalDateTime.now();

        return recoveryCode.equals(userRegisterCode.getCode()) && now.isBefore(timeout);
    }
}
