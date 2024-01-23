package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import com.tcc.uffmaterias.domain.model.jpa.Usuarios;
import com.tcc.uffmaterias.domain.model.redis.UserRegister;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterRepository;
import com.tcc.uffmaterias.dto.request.RegisterDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserRegisterService{

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional
    public void saveUserRegisterToRedis(RegisterDto registerDto, UsuarioTipo role){
        var userRegisterOptional = userRegisterRepository.findByEmail(registerDto.getEmail());
        userRegisterOptional.ifPresent(userRegister -> userRegisterRepository.delete(userRegister));
        var password = passwordEncoder.encode(registerDto.getPassword());
        UserRegister userRegister = UserRegister.builder()
                .email(registerDto.getEmail())
                .nome(registerDto.getNome())
                .celular(registerDto.getCelular())
                .password(password)
                .usuarioTipo(role)
                .build();
        userRegisterRepository.save(userRegister);
        stringRedisTemplate.expire(userRegister.getEmail(),2, TimeUnit.MINUTES);
    }

    @Transactional
    public void saveUserRegisterToJpa(UserRegister userRegister){
        Usuarios usuarios = new Usuarios();
        usuarios.setNome(userRegister.getNome());
        usuarios.setEmail(userRegister.getEmail());
        usuarios.setSenha(userRegister.getPassword());
        usuarios.setCelular(userRegister.getCelular());
        usuarios.setUsuarioTipo(userRegister.getUsuarioTipo());
        usuarioRepository.save(usuarios);
    }
}
