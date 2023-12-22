package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.repository.MateriasRepository;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriasService {

    @Autowired
    private MateriasRepository materiasRepository;

    public List<Materias> listarMaterias(){
        return materiasRepository.findAll();
    }


    public Materias buscaMateria(Long id){
        return materiasRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Matéria não encontrada"));
    }

}
