package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.jpa.Usuarios;
import com.tcc.uffmaterias.domain.model.redis.UserRegister;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioTipoRepository;
import com.tcc.uffmaterias.domain.repository.redis.UserRegisterRepository;
import com.tcc.uffmaterias.dto.request.RegisterDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
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

    @Autowired
    private UsuarioTipoRepository usuarioTipoRepository;

    @Transactional
    public void saveUserRegisterToRedis(RegisterDto registerDto){
        var userRegisterOptional = userRegisterRepository.findByEmail(registerDto.getEmail());
        userRegisterOptional.ifPresent(userRegister -> userRegisterRepository.delete(userRegister));
        var password = passwordEncoder.encode(registerDto.getPassword());
        UserRegister userRegister = UserRegister.builder()
                .email(registerDto.getEmail())
                .nome(registerDto.getNome())
                .celular(registerDto.getCelular())
                .password(password)
                .build();
        userRegisterRepository.save(userRegister);
        stringRedisTemplate.expire(userRegister.getEmail(),2, TimeUnit.MINUTES);
    }

    @Transactional
    public void saveUserRegisterToJpa(UserRegister userRegister){
        var usuarioTipo = usuarioTipoRepository.findById(1L).orElseThrow(
                ()-> new NotFoundException("Tipo de usuario n encontrado")
        );
        Usuarios usuarios = new Usuarios();
        usuarios.setNome(userRegister.getNome());
        usuarios.setEmail(userRegister.getEmail());
        usuarios.setSenha(userRegister.getPassword());
        usuarios.setCelular(userRegister.getCelular());
        usuarios.setUsuarioTipo(usuarioTipo);
        usuarioRepository.save(usuarios);
    }
}
