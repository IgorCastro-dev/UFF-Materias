package com.tcc.uffmaterias.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
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
            salvarArquivo(conteudoSecaoRequestDto.getArquivo(), fileNome);
        }catch (Exception e){
            throw new StorageException("Não foi possível enviar o arquivo para a AmazonS3",e);
        }
    }

    public void atualizar(MultipartFile arquivo, String novoNome, String nomeAntigo) {
        try {
            deletar(nomeAntigo);
            salvarArquivo(arquivo, novoNome);
        }catch (Exception e){
            throw new StorageException("Não foi possível atualizar o arquivo para a AmazonS3",e);
        }
    }

    public void deletar(String nome) {
        try {
            String caminhoArquivo = getCaminho(nome);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    BUCKET,
                    caminhoArquivo
            );
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (AmazonS3Exception e) {
            throw new StorageException("Não foi possível deletar o arquivo na AmazonS3",e);
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

    private void salvarArquivo(MultipartFile arquivo, String nome) throws IOException {
        String caminhoArquivo = getCaminho(nome);
        InputStream inputStream = arquivo.getInputStream();
        var objectMetadata = new ObjectMetadata();
        var putObjectRequest = new PutObjectRequest(
                BUCKET,
                caminhoArquivo,
                inputStream,
                objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
    }

    private String getCaminho(String fileNome) {
        return String.format("%s/%s",DIRETORIO_CONTEUDO,fileNome);
    }

    
}
