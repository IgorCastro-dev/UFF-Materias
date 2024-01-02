package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.service.SecaoMateriasService;
import com.tcc.uffmaterias.dto.request.SecaoMateriaRequestDto;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "Listar os Tópicos por id da matéria")
    @GetMapping("materia/{materia_id}")
    public ResponseEntity<List<SecaoMateriaResponseDto>> listarTopicosPorMateria(@PathVariable("materia_id") Long id){
        return ResponseEntity.ok(secaoMateriasService.listarSecaoMateriasPorMateria(id));
    }

    @Operation(summary = "Buscar Tópico por Id")
    @GetMapping("/{topico_id}")
    public ResponseEntity<SecaoMateriaResponseDto> buscarTopicoPorId(@PathVariable("topico_id") Long id){
        return ResponseEntity.ok(secaoMateriasService.buscarSecaoMateriaPorId(id));
    }

    @Operation(summary = "Atualiza Tópico por Id")
    @PutMapping("/{topico_id}")
    public ResponseEntity<SecaoMateriaResponseDto> atualizarTopicoPorId(
            @PathVariable("topico_id") Long id,
            @Valid @RequestBody SecaoMateriaRequestDto secaoMateriaRequestDto){
        return ResponseEntity.ok(secaoMateriasService.atualizaMateriaporId(id,secaoMateriaRequestDto));
    }

    @Operation(summary = "Salva Tópico passando o id da matéria no parâmetro")
    @PostMapping("/{materia_id}")
    public ResponseEntity<SecaoMateriaResponseDto> salvarTopico(
            @PathVariable("materia_id") Long id,
            @Valid @RequestBody SecaoMateriaRequestDto secaoMateriaRequestDto){
        return ResponseEntity.ok(secaoMateriasService.salvaSecaoMateria(id,secaoMateriaRequestDto));
    }
}
