package com.tcc.uffmaterias.domain.controller;


import com.tcc.uffmaterias.domain.service.ConteudoSecaoService;
import com.tcc.uffmaterias.dto.request.ConteudoSecaoRequestDto;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conteudo")
public class ConteudoSecaoController {

    @Autowired
    private ConteudoSecaoService conteudoSecaoService;

    @PostMapping("/{topicoId}")
    public ResponseEntity<ConteudoSecaoResponseDto> uploadConteudo(
            @PathVariable("topicoId") Long secaoMateriaId,
            @Valid ConteudoSecaoRequestDto conteudoSecaoRequestDto){
        return ResponseEntity.ok(conteudoSecaoService.uploadConteudoSecao(secaoMateriaId,conteudoSecaoRequestDto));
    }
}
