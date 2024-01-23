package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto{
        @NotBlank
        String nome;
        @Email
        String email;
        @NotBlank
        @Size(min = 11)
        String celular;
        @NotBlank
        String password;
        @NotNull
        Long roleId;
}
