package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.SecaoMateriaMapper;
import com.tcc.uffmaterias.domain.repository.SecaoMateriaRepository;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecaoMateriasService {
    @Autowired
    private SecaoMateriaRepository secaoMateriaRepository;

    @Autowired
    private SecaoMateriaMapper secaoMateriaMapper;
    public List<SecaoMateriaResponseDto> listarSecaoMaterias(){
        return secaoMateriaMapper.listEntityToListDto(secaoMateriaRepository.findAll());
    }
}
