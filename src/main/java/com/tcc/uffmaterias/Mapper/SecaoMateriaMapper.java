package com.tcc.uffmaterias.Mapper;
import com.tcc.uffmaterias.domain.model.jpa.SecaoMaterias;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecaoMateriaMapper {
    @Autowired
    private ModelMapper modelMapper;

    public SecaoMateriaResponseDto entityToDto(SecaoMaterias secaoMateria){
        return modelMapper.map(secaoMateria,SecaoMateriaResponseDto.class);
    }

    public List<SecaoMateriaResponseDto> listEntityToListDto(List<SecaoMaterias> secaoMaterias){
        return secaoMaterias.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
