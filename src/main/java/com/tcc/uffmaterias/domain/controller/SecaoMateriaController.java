package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.service.SecaoMateriasService;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Gerencia os tópicos")
public class SecaoMateriaController {
    @Autowired
    private SecaoMateriasService secaoMateriasService;
    @Operation(summary = "Listar todos os Tópicos")
    @GetMapping
    public ResponseEntity<List<SecaoMateriaResponseDto>> listarTopicos(){
        return ResponseEntity.ok(secaoMateriasService.listarSecaoMaterias());
    }
}
