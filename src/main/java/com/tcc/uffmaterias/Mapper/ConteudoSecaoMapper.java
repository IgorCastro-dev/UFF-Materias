package com.tcc.uffmaterias.Mapper;

import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConteudoSecaoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ConteudoSecaoResponseDto entityToDto(ConteudoSecao conteudoSecao){
        return modelMapper.map(conteudoSecao,ConteudoSecaoResponseDto.class);
    }
}
