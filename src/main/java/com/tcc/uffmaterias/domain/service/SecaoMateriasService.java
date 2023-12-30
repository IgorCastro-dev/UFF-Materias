package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.SecaoMateriaMapper;
import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import com.tcc.uffmaterias.domain.repository.SecaoMateriaRepository;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
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

    public SecaoMateriaResponseDto buscarSecaoMateriaPorId(Long id){
        return secaoMateriaMapper.entityToDto(getSecaoMateria(id));
    }

    private SecaoMaterias getSecaoMateria(Long id) {
        return secaoMateriaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Matéria não encontrada"));
    }
}