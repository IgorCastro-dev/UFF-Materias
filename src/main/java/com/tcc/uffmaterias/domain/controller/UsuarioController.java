package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.model.jpa.Usuarios;
import com.tcc.uffmaterias.domain.service.RegisterService;
import com.tcc.uffmaterias.domain.service.UsuarioService;
import com.tcc.uffmaterias.dto.request.CodeDto;
import com.tcc.uffmaterias.dto.request.EmailDto;
import com.tcc.uffmaterias.dto.request.RegisterDto;
import com.tcc.uffmaterias.dto.request.UpdatePasswordDto;
import com.tcc.uffmaterias.dto.request.UsuarioRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import org.springframework.security.core.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Gerencia os usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegisterService registerService;


    @Operation(summary = "Lista os usuarios")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Busca o usuário por id")
    @GetMapping("/{usuario_id}")
    public ResponseEntity<Usuarios> buscaUsuario(@PathVariable("usuario_id") Long id){
        return ResponseEntity.ok(usuarioService.buscaUsuario(id));
    }

    @Operation(summary = "Exclui usuário por id")
    @DeleteMapping("/{usuario_id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuario_id") Long id){
        usuarioService.deletaUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualiza usuário por id")
    @PutMapping("/{usuario_id}")
    public ResponseEntity<Usuarios> atualizarUsuario(@PathVariable("usuario_id") Long id,@Valid @RequestBody UsuarioRequestDto usuarioRequestDto){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id,usuarioRequestDto));
    }
    @Operation(summary = "Salva usuário no redis e envia o código")
    @PostMapping
    public ResponseEntity<Void> salvaUsuarioNoRedis(@Valid @RequestBody RegisterDto registerDto){
        registerService.register(registerDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Verifica o código de registro de usuário e salva no Mysql")
    @PostMapping("/verify-registercode")
    public ResponseEntity<Void> verifyRegisterCode(
            @Email @PathParam("email") String email,
            @RequestBody CodeDto codeDto){
        registerService.confirmRegisterCode(codeDto.getCode(),email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Verifica o código de registro de usuário e salva no Mysql")
    @PostMapping("/verify-recoverycode")
    public ResponseEntity<Boolean> verifyRecoveryCode(
            @Email @PathParam("email") String email,
            @RequestBody CodeDto codeDto){
        return ResponseEntity.ok(registerService.confirmRecoveryCode(codeDto.getCode(),email));
    }


    @Operation(summary = "Verifica o email e envia o código de verificação")
    @PostMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(
            @RequestBody EmailDto emailDto){
        registerService.confirmEmail(emailDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Faz a atualização da senha passando o email, código e a senha no corpo")
    @PutMapping("/update-password")
    public ResponseEntity<Void> verifyRecoveryCode(
            @RequestBody UpdatePasswordDto updatePasswordDto){
        registerService.confirmRecoveryCode(updatePasswordDto.getCode(),updatePasswordDto.getEmail());
        usuarioService.updatePassword(updatePasswordDto.getPassword(),updatePasswordDto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
