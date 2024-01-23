package com.tcc.uffmaterias.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

@Validated
@Builder
@Data
public class Menssagem {

    @Singular
    private Set<String> destinatarios;

    @NonNull
    private String assunto;

    @NonNull
    private String corpo;

    @Singular("variavel")
    private Map<String,Object> variaveis;
}
