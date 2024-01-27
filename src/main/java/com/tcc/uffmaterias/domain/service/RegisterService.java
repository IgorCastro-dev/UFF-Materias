package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.repository.jpa.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioTipoRepository;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterRepository;
import com.tcc.uffmaterias.dto.request.EmailDto;
import com.tcc.uffmaterias.dto.request.LoginDto;
import com.tcc.uffmaterias.dto.request.RegisterDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
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
    private TokenServiceImpl tokenService;

    @Autowired
    private CodeService codeService;
    @Transactional
    public void register(RegisterDto registerDTO) {
        var userCredentialsOptional = usuarioRepository.findByEmail(registerDTO.getEmail());
        if(userCredentialsOptional.isPresent()){
            throw new RuntimeException("Usuário já existe");
        }
        userRegisterService.saveUserRegisterToRedis(registerDTO);
        codeService.sendRegisterCode(registerDTO.getEmail());
    }

    public void confirmRegisterCode(String code,String email) {
        if(Boolean.TRUE.equals(codeService.isValidRegisterCode(code,email))){
            var user = userRegisterRepository.findByEmail(email).orElseThrow(
                    ()-> new NotFoundException("Usuario não encontrado"));
            userRegisterService.saveUserRegisterToJpa(user);
        }else throw new NotFoundException("Código inválido");
    }

    public boolean confirmRecoveryCode(String code, String email) {
        if(Boolean.TRUE.equals(codeService.isValidRecoveryCode(code,email))){
            return true;
        }else throw new NotFoundException("Código inválido");
    }

    public void confirmEmail(EmailDto emailDto) {
        usuarioRepository.findByEmail(emailDto.getEmail())
                .orElseThrow(()->new NotFoundException("Usuário não existe"));
        codeService.sendRecoveryCode(emailDto.getEmail());
    }


}
