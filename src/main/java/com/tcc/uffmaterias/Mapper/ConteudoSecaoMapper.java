package com.tcc.uffmaterias.Mapper;

import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.domain.service.S3ConteudoService;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConteudoSecaoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private S3ConteudoService s3ConteudoService;

    public ConteudoSecaoResponseDto entityToDto(ConteudoSecao conteudoSecao){
        return modelMapper.map(conteudoSecao,ConteudoSecaoResponseDto.class);
    }

    public List<ConteudoSecaoResponseDto> listEntityToListDto(List<ConteudoSecao> conteudoSecoes){
        return conteudoSecoes.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
