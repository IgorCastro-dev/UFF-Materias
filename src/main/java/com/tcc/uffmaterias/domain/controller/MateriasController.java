package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.service.MateriasService;
import com.tcc.uffmaterias.dto.request.MateriaRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/materias")
@Tag(name = "Matéria", description = "Gerencia as matérias")
public class MateriasController {

    @Autowired
    private MateriasService materiasService;

    @Operation(summary = "Listar todas as matérias")
    @GetMapping
    public ResponseEntity<List<Materias>> listarMaterias(){
        return ResponseEntity.ok(materiasService.listarMaterias());
    }

    @Operation(summary = "Busca a matérias por id")
    @GetMapping("/{materia_id}")
    public ResponseEntity<Materias> buscarMaterias(@PathVariable("materia_id") Long id){
        return ResponseEntity.ok(materiasService.buscaMateria(id));
    }

    @Operation(summary = "Atualiza usuário por id")
    @PutMapping("/{materia_id}")
    public ResponseEntity<Materias> atualizarMaterias(@PathVariable("materia_id") Long id, @Valid @RequestBody MateriaRequestDto materiaRequestDto){
        return ResponseEntity.ok(materiasService.atualizarMateria(id,materiaRequestDto));
    }
}
