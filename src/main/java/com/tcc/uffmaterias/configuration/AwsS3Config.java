package com.tcc.uffmaterias.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Value("${uffmaterias.storage.s3.id-chave-acesso}")
    private String ID_CHAVE_ACESSO;
    @Value("${uffmaterias.storage.s3.chave-acesso-secreta}")
    private String CHAVE_ACESSO_SECRETA;
    @Value("${uffmaterias.storage.s3.regiao}")
    private String REGIAO;

    @Bean
    public AmazonS3 amazonS3(){
        var credentials = new BasicAWSCredentials(ID_CHAVE_ACESSO,CHAVE_ACESSO_SECRETA);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(REGIAO)
                .build();
    }
}
