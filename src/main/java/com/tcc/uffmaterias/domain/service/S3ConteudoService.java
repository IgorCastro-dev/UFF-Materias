package com.tcc.uffmaterias.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.dto.request.ConteudoSecaoRequestDto;
import com.tcc.uffmaterias.error.erros.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class S3ConteudoService {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${uffmaterias.storage.s3.diretorio-conteudo}")
    private String DIRETORIO_CONTEUDO;
    @Value("${uffmaterias.storage.s3.bucket}")
    private String BUCKET;
    public void armazenar(ConteudoSecaoRequestDto conteudoSecaoRequestDto,Long conteudoId) {
        try {
            String caminhoArquivo = getCaminho(conteudoSecaoRequestDto.getDescricao(),conteudoId);
            InputStream inputStream = conteudoSecaoRequestDto.getArquivo().getInputStream();
            var objectMetadata = new ObjectMetadata();
            var putObjectRequest = new PutObjectRequest(BUCKET,caminhoArquivo,inputStream,objectMetadata);
            amazonS3.putObject(putObjectRequest);
        }catch (Exception e){
            throw new StorageException("Não foi possível enviar o arquivo para a AmazonS3",e);
        }
    }

    public URL buscarUrlArquivoS3(String descricao, Long conteudoId){
        try {
            var caminhoArquivo = getCaminho(descricao,conteudoId);
            return amazonS3.getUrl(BUCKET,caminhoArquivo);
        }catch (Exception e){
            throw new StorageException("Não foi possível buscar o arquivo da AmazonS3",e);
        }
    }

    private String getCaminho(String descricao,Long conteudoId) {
        return String.format("%s/%s_%d",DIRETORIO_CONTEUDO,descricao,conteudoId);
    }
}
