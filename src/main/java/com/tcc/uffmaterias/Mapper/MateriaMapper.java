package com.tcc.uffmaterias.Mapper;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.dto.response.MateriaResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MateriaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public MateriaResponseDto entityToDto(Materias materias){
        return modelMapper.map(materias,MateriaResponseDto.class);
    }

    public List<MateriaResponseDto> listEntityToListDto(List<Materias> materias){
        return materias.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
