package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.Mapper.ConteudoSecaoMapper;
import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import com.tcc.uffmaterias.domain.repository.ConteudoSecaoRepository;
import com.tcc.uffmaterias.dto.request.ConteudoSecaoRequestDto;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConteudoSecaoService {

    @Autowired
    private ConteudoSecaoMapper conteudoSecaoMapper;
    @Autowired
    private SecaoMateriasService secaoMateriasService;
    @Autowired
    private S3ConteudoService s3ConteudoService;
    @Autowired
    private ConteudoSecaoRepository conteudoSecaoRepository;

    @Transactional
    public ConteudoSecaoResponseDto uploadConteudoSecao(
            Long secaoMateriaId,
            ConteudoSecaoRequestDto conteudoSecaoRequestDto){
        ConteudoSecao conteudoSecao = dtoToEntity(secaoMateriaId,conteudoSecaoRequestDto);
        conteudoSecaoRepository.save(conteudoSecao);
        conteudoSecaoRepository.flush();
        s3ConteudoService.armazenar(conteudoSecaoRequestDto,conteudoSecao.getNome());
        return conteudoSecaoMapper.entityToDto(conteudoSecao);
    }

    public ConteudoSecaoResponseDto buscarConteudo(Long conteudoId){
        ConteudoSecao conteudoSecao = conteudoSecaoRepository.findById(conteudoId).orElseThrow(
                () -> new NotFoundException("Conteúdo não encontrado"));
        return conteudoSecaoMapper.entityToDto(conteudoSecao);
    }

    public List<ConteudoSecaoResponseDto> listarConteudos(Long secaoMateriaId){
        SecaoMaterias secaoMaterias = secaoMateriasService.getSecaoMateria(secaoMateriaId);
        return conteudoSecaoMapper.listEntityToListDto(conteudoSecaoRepository.findAllBySecaoMaterias(secaoMaterias));
    }

    public byte[] dowloadConteudo(String fileNome){
        conteudoSecaoRepository.findByNome(fileNome).orElseThrow(
                () -> new NotFoundException("Conteúdo não encontrado"));
        return s3ConteudoService.buscarUrlArquivoS3(fileNome);
    }

        private ConteudoSecao dtoToEntity(Long secaoMateriaId,ConteudoSecaoRequestDto conteudoSecaoRequestDto) {
        SecaoMaterias secaoMaterias = secaoMateriasService.getSecaoMateria(secaoMateriaId);
        ConteudoSecao conteudoSecao = new ConteudoSecao();
        conteudoSecao.setNome(conteudoSecaoRequestDto.getArquivo().getOriginalFilename());
        conteudoSecao.setConteudoDaSecao(conteudoSecaoRequestDto.getArquivo().getContentType());
        conteudoSecao.setDescricao(conteudoSecaoRequestDto.getDescricao());
        conteudoSecao.setSecaoMaterias(secaoMaterias);
        return conteudoSecao;
    }
}
