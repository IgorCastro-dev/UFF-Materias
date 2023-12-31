package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SecaoMateriaRequestDto {

    @NotBlank
    private String secaoMateriasNome;

}
