package com.tcc.uffmaterias.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MateriaRequestDto {
    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 8)
    private String codigo;
}
