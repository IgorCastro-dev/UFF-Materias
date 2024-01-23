package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.MateriaMapper;
import com.tcc.uffmaterias.domain.model.jpa.Materias;
import com.tcc.uffmaterias.domain.repository.jpa.MateriasRepository;
import com.tcc.uffmaterias.dto.request.MateriaRequestDto;
import com.tcc.uffmaterias.dto.response.MateriaResponseDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriasService {

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MateriaMapper materiaMapper;

    public List<MateriaResponseDto> listarMaterias(){
        return  materiaMapper.listEntityToListDto(materiasRepository.findAll());
    }

    public MateriaResponseDto buscaMateria(Long id){
        return materiaMapper.entityToDto(getMaterias(id));
    }

    @Transactional
    public MateriaResponseDto atualizarMateria(Long id, MateriaRequestDto materiaRequestDto) {
        Materias materias = getMaterias(id);
        modelMapper.map(materiaRequestDto,materias);
        materiasRepository.save(materias);
        return materiaMapper.entityToDto(materias);
    }

    @Transactional
    public void deletaMateria(Long id){
        getMaterias(id);
        materiasRepository.deleteById(id);
    }

    @Transactional
    public MateriaResponseDto salvaMateria(MateriaRequestDto materiaRequestDto){
        Materias materias = modelMapper.map(materiaRequestDto,Materias.class);
        return materiaMapper.entityToDto(materiasRepository.save(materias));
    }

    public Materias getMaterias(Long id) {
        return materiasRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Matéria não encontrada"));
    }
}
