package com.tcc.uffmaterias.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDto {

    private String nome;

    private String email;

    @NotBlank
    private String celular;

    @NotBlank
    private String usuarioTipo;
}
