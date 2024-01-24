package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailDto {

    @Email
    private String email;
}
