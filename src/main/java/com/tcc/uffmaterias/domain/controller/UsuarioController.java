package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }
}
