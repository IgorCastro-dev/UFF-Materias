package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.redis.UserRecoveryCode;
import com.tcc.uffmaterias.domain.model.redis.UserRegisterCode;
import com.tcc.uffmaterias.domain.repository.redis.UserRecoveryCodeRepository;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterCodeRepository;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import com.tcc.uffmaterias.evento.publisher.CadastroPublisher;
import com.tcc.uffmaterias.evento.publisher.RecoveryPublisher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CodeService {

    @Value("${webservice.igor.redis.recoverycode.timeout}")
    private String time;

    @Autowired
    private UserRegisterCodeRepository userRegisterCodeRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    private CadastroPublisher cadastroPublisher;

    @Autowired
    private RecoveryPublisher recoveryPublisher;

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
        cadastroPublisher.publicarEvento(userRegisterCode.getEmail(),userRegisterCode.getCode());
    }

    public void sendRecoveryCode(String email) {
        String code = String.format("%04d",new Random().nextInt(10000));
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();
        var userRecoveryCodeOptional = userRecoveryCodeRepository.findByEmail(email);
        if (userRecoveryCodeOptional.isEmpty()){
            userRecoveryCode.setEmail(email);
        }else {
            userRecoveryCode = userRecoveryCodeOptional.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setDateTime(LocalDateTime.now());
        userRecoveryCodeRepository.save(userRecoveryCode);
        recoveryPublisher.publicarEvento(userRecoveryCode.getEmail(), userRecoveryCode.getCode());
    }

    public Boolean isValidRegisterCode(String code, String email) {
        UserRegisterCode userRegisterCode = userRegisterCodeRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Código não encontrado")
        );
        LocalDateTime timeout = userRegisterCode.getDateTime().plusMinutes(Long.parseLong(time));
        LocalDateTime now = LocalDateTime.now();

        return code.equals(userRegisterCode.getCode()) && now.isBefore(timeout);
    }

    public Boolean isValidRecoveryCode(String code, String email) {
        UserRecoveryCode userRecoveryCode = userRecoveryCodeRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Código não encontrado")
        );
        LocalDateTime timeout = userRecoveryCode.getDateTime().plusMinutes(Long.parseLong(time));
        LocalDateTime now = LocalDateTime.now();

        return code.equals(userRecoveryCode.getCode()) && now.isBefore(timeout);
    }


}
