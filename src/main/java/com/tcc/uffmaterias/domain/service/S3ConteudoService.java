package com.tcc.uffmaterias.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
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
    public void armazenar(ConteudoSecaoRequestDto conteudoSecaoRequestDto,String fileNome) {
        try {
            String caminhoArquivo = getCaminho(fileNome);
            InputStream inputStream = conteudoSecaoRequestDto.getArquivo().getInputStream();
            var objectMetadata = new ObjectMetadata();
            var putObjectRequest = new PutObjectRequest(
                    BUCKET,
                    caminhoArquivo,
                    inputStream,
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        }catch (Exception e){
            throw new StorageException("Não foi possível enviar o arquivo para a AmazonS3",e);
        }
    }

    public byte[] buscarUrlArquivoS3(String fileNome){
        try {
            var caminhoArquivo = getCaminho(fileNome);
            S3Object s3Object = amazonS3.getObject(BUCKET,caminhoArquivo);
            InputStream inputStream = s3Object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        }catch (Exception e){
            throw new StorageException("Não foi possível buscar o arquivo da AmazonS3",e);
        }
    }

    private String getCaminho(String fileNome) {
        return String.format("%s/%s",DIRETORIO_CONTEUDO,fileNome);
    }
}
