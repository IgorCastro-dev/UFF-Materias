package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
