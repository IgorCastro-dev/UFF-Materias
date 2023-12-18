package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Gerencia os usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Lista os usuarios")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Exclui usuário por id")
    @DeleteMapping("/{usuario_id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuario_id") Long id){
        usuarioService.deletaUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
