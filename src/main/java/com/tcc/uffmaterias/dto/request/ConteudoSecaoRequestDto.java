package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConteudoSecaoRequestDto {
    @NotNull
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;
}
