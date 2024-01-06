package com.tcc.uffmaterias.Mapper;

import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.domain.service.S3ConteudoService;
import com.tcc.uffmaterias.dto.response.ConteudoSecaoResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConteudoSecaoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private S3ConteudoService s3ConteudoService;

    public ConteudoSecaoResponseDto entityToDto(ConteudoSecao conteudoSecao){
        ConteudoSecaoResponseDto dto = modelMapper.map(conteudoSecao,ConteudoSecaoResponseDto.class);
        dto.setUrl(s3ConteudoService.buscarUrlArquivoS3(conteudoSecao.getDescricao()
                    ,conteudoSecao.getConteudoSecaoId()).toString());
        return dto;
    }
}
