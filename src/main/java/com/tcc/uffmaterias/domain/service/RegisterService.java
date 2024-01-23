package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import com.tcc.uffmaterias.domain.model.redis.UserRegisterCode;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioTipoRepository;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterRepository;
import com.tcc.uffmaterias.dto.request.RegisterDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UsuarioTipoRepository usuarioTipoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @Autowired
    private RegisterCodeService registerCodeService;
    @Transactional
    public void register(RegisterDto registerDTO) {
        var userCredentialsOptional = usuarioRepository.findByEmail(registerDTO.getEmail());
        if(userCredentialsOptional.isPresent()){
            throw new RuntimeException("Usuário já existe");
        }
        UsuarioTipo role = usuarioTipoRepository.findById(registerDTO.getRoleId()).orElseThrow(
                ()->new RuntimeException("Role não existe")
        );
        userRegisterService.saveUserRegisterToRedis(registerDTO,role);
        registerCodeService.sendRegisterCode(registerDTO.getEmail());
    }

    public void confirmCode(String code,String email) {
        if(Boolean.TRUE.equals(registerCodeService.isValidCode(code,email))){
            var user = userRegisterRepository.findByEmail(email).orElseThrow(
                    ()-> new NotFoundException("Usuario não encontrado"));
            userRegisterService.saveUserRegisterToJpa(user);
        }else throw new NotFoundException("Código inválido");
    }

}
