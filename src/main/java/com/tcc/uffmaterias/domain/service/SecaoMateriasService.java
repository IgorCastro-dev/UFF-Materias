package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.SecaoMateriaMapper;
import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import com.tcc.uffmaterias.domain.repository.MateriasRepository;
import com.tcc.uffmaterias.domain.repository.SecaoMateriaRepository;
import com.tcc.uffmaterias.dto.request.SecaoMateriaRequestDto;
import com.tcc.uffmaterias.dto.response.SecaoMateriaResponseDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecaoMateriasService {

    @Autowired
    private MateriasService materiasService;
    @Autowired
    private SecaoMateriaRepository secaoMateriaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SecaoMateriaMapper secaoMateriaMapper;
    public List<SecaoMateriaResponseDto> listarSecaoMaterias(){
        return secaoMateriaMapper.listEntityToListDto(secaoMateriaRepository.findAll());
    }

    public SecaoMateriaResponseDto buscarSecaoMateriaPorId(Long id){
        return secaoMateriaMapper.entityToDto(getSecaoMateria(id));
    }

    public List<SecaoMateriaResponseDto> listarSecaoMateriasPorMateria(Long materiId){
        Materias materias = materiasService.getMaterias(materiId);
        return secaoMateriaMapper.listEntityToListDto(secaoMateriaRepository.findAllByMateria(materias));
    }

    public SecaoMateriaResponseDto atualizaMateriaporId(Long id, SecaoMateriaRequestDto secaoMateriaRequestDto){
        SecaoMaterias secaoMaterias = getSecaoMateria(id);
        modelMapper.map(secaoMateriaRequestDto,secaoMaterias);
        secaoMateriaRepository.save(secaoMaterias);
        return secaoMateriaMapper.entityToDto(secaoMaterias);
    }

    public SecaoMateriaResponseDto salvaSecaoMateria(Long materiaId,SecaoMateriaRequestDto secaoMateriaRequestDto){
        SecaoMaterias secaoMaterias = modelMapper.map(secaoMateriaRequestDto,SecaoMaterias.class);
        Materias materias = materiasService.getMaterias(materiaId);
        secaoMaterias.setMateria(materias);
        SecaoMaterias secaoMateriasSalva = secaoMateriaRepository.save(secaoMaterias);
        return secaoMateriaMapper.entityToDto(secaoMateriasSalva);
    }

    private SecaoMaterias getSecaoMateria(Long id) {
        return secaoMateriaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Matéria não encontrada"));
    }
}
