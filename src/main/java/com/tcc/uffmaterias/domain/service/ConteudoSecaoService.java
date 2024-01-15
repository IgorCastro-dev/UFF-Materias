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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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

    @Transactional
    public ConteudoSecaoResponseDto atualizaConteudoSecao(
            Long conteudoId,
            MultipartFile arquivo,
            String descricao) {
        ConteudoSecao conteudoSecaoAtual = getConteudoById(conteudoId);
        String nomeAntigo = conteudoSecaoAtual.getNome();
        if (Objects.isNull(arquivo)){
            conteudoSecaoAtual.setDescricao(descricao);
            conteudoSecaoRepository.save(conteudoSecaoAtual);
            return conteudoSecaoMapper.entityToDto(conteudoSecaoAtual);
        }
        ConteudoSecao conteudoSecaoNovo = getConteudoSecaoNovo(arquivo, descricao,conteudoSecaoAtual);
 //       conteudoSecaoRepository.save(conteudoSecaoNovo);
        conteudoSecaoRepository.flush();
        s3ConteudoService.atualizar(arquivo,conteudoSecaoNovo.getNome(),nomeAntigo);
        return conteudoSecaoMapper.entityToDto(conteudoSecaoNovo);
    }

    public ConteudoSecaoResponseDto buscarConteudo(Long conteudoId){
        ConteudoSecao conteudoSecao = getConteudoById(conteudoId);
        return conteudoSecaoMapper.entityToDto(conteudoSecao);
    }

    public List<ConteudoSecaoResponseDto> listarConteudos(Long secaoMateriaId){
        SecaoMaterias secaoMaterias = secaoMateriasService.getSecaoMateria(secaoMateriaId);
        return conteudoSecaoMapper.listEntityToListDto(conteudoSecaoRepository.findAllBySecaoMaterias(secaoMaterias));
    }

    public byte[] dowloadConteudo(String fileNome){
        getConteudoByName(fileNome);
        return s3ConteudoService.buscarUrlArquivoS3(fileNome);
    }

    @Transactional
    public void deletaConteudoByNome(String fileNome) {
        ConteudoSecao conteudoSecao = getConteudoByName(fileNome);
        conteudoSecaoRepository.delete(conteudoSecao);
        conteudoSecaoRepository.flush();
        s3ConteudoService.deletar(fileNome);
    }

    private ConteudoSecao getConteudoByName(String fileNome) {
        return conteudoSecaoRepository.findByNome(fileNome).orElseThrow(
                () -> new NotFoundException("Conteúdo não encontrado"));
    }



    private ConteudoSecao getConteudoSecaoNovo(MultipartFile arquivo, String descricao,ConteudoSecao conteudoSecaoAtual) {
        conteudoSecaoAtual.setDescricao(descricao);
        conteudoSecaoAtual.setConteudoDaSecao(arquivo.getContentType());
        conteudoSecaoAtual.setNome(arquivo.getOriginalFilename());
        return conteudoSecaoAtual;
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

    private ConteudoSecao getConteudoById(Long conteudoId) {
        ConteudoSecao conteudoSecao = conteudoSecaoRepository.findById(conteudoId).orElseThrow(
                () -> new NotFoundException("Conteúdo não encontrado"));
        return conteudoSecao;
    }

    
}
