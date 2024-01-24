package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {

    @Email
    private String email;

    @NotBlank
    private String code;

    @NotBlank
    private String password;
}
