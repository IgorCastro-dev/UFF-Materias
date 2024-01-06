package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.ConteudoSecaoMapper;
import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import com.tcc.uffmaterias.domain.repository.ConteudoSecaoRepository;
import com.tcc.uffmaterias.dto.request.ConteudoSecaoRequestDto;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ConteudoSecaoService {

    @Autowired
    private ConteudoSecaoMapper conteudoSecaoMapper;
    @Autowired
    private SecaoMateriasService secaoMateriasService;
    @Autowired
    private S3ConteudoService s3ConteudoService;
    @Autowired
    private ConteudoSecaoRepository conteudoSecaoRepository;

    @Transactional
    public ConteudoSecaoResponseDto uploadConteudoSecao(
            Long secaoMateriaId,
            ConteudoSecaoRequestDto conteudoSecaoRequestDto){
        ConteudoSecao conteudoSecao = dtoToEntity(secaoMateriaId,conteudoSecaoRequestDto);
        conteudoSecaoRepository.save(conteudoSecao);
        conteudoSecaoRepository.flush();
        s3ConteudoService.armazenar(conteudoSecaoRequestDto,conteudoSecao.getConteudoSecaoId());
        return conteudoSecaoMapper.entityToDto(conteudoSecao);
    }

    private ConteudoSecao dtoToEntity(Long secaoMateriaId,ConteudoSecaoRequestDto conteudoSecaoRequestDto) {
        SecaoMaterias secaoMaterias = secaoMateriasService.getSecaoMateria(secaoMateriaId);
        ConteudoSecao conteudoSecao = new ConteudoSecao();
        conteudoSecao.setConteudoDaSecao(conteudoSecaoRequestDto.getArquivo().getContentType());
        conteudoSecao.setDescricao(conteudoSecaoRequestDto.getDescricao());
        conteudoSecao.setSecaoMaterias(secaoMaterias);
        return conteudoSecao;
    }
}
