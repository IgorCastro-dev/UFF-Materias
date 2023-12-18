package com.tcc.uffmaterias.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDto {

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String celular;

    @NotBlank
    private String usuarioTipo;
}
