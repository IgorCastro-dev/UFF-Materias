package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.repository.MateriasRepository;
import com.tcc.uffmaterias.dto.request.MateriaRequestDto;
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

    public List<Materias> listarMaterias(){
        return materiasRepository.findAll();
    }

    public Materias buscaMateria(Long id){
        return getMaterias(id);
    }

    @Transactional
    public Materias atualizarMateria(Long id, MateriaRequestDto materiaRequestDto) {
        Materias materias = getMaterias(id);
        modelMapper.map(materiaRequestDto,materias);
        materiasRepository.save(materias);
        return materias;
    }

    @Transactional
    public void deletaMateria(Long id){
        getMaterias(id);
        materiasRepository.deleteById(id);
    }

    @Transactional
    public Materias salvaMateria(MateriaRequestDto materiaRequestDto){
        Materias materias = modelMapper.map(materiaRequestDto,Materias.class);
        return materiasRepository.save(materias);
    }

    private Materias getMaterias(Long id) {
        return materiasRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Matéria não encontrada"));
    }
}
