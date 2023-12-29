package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.service.MateriasService;
import com.tcc.uffmaterias.dto.request.MateriaRequestDto;
import com.tcc.uffmaterias.dto.response.MateriaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/materias")
@Tag(name = "Matéria", description = "Gerencia as matérias")
public class MateriasController {

    @Autowired
    private MateriasService materiasService;

    @Operation(summary = "Listar todas as matérias")
    @GetMapping
    public ResponseEntity<List<MateriaResponseDto>> listarMaterias(){
        return ResponseEntity.ok(materiasService.listarMaterias());
    }

    @Operation(summary = "Busca a matérias por id")
    @GetMapping("/{materia_id}")
    public ResponseEntity<MateriaResponseDto> buscarMaterias(@PathVariable("materia_id") Long id){
        return ResponseEntity.ok(materiasService.buscaMateria(id));
    }

    @Operation(summary = "Atualiza matéria por id")
    @PutMapping("/{materia_id}")
    public ResponseEntity<MateriaResponseDto> atualizarMaterias(@PathVariable("materia_id") Long id, @Valid @RequestBody MateriaRequestDto materiaRequestDto){
        return ResponseEntity.ok(materiasService.atualizarMateria(id,materiaRequestDto));
    }

    @Operation(summary = "Excluí matéria por id")
    @DeleteMapping("/{materia_id}")
    public ResponseEntity<Void> deletaMateria(@PathVariable("materia_id") Long id){
        materiasService.deletaMateria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Salva matéria")
    @PostMapping()
    public ResponseEntity<MateriaResponseDto> salvarMateria(@Valid @RequestBody MateriaRequestDto materiaRequestDto){
        return ResponseEntity.ok(materiasService.salvaMateria(materiaRequestDto));
    }
}
