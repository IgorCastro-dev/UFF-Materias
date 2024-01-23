package com.tcc.uffmaterias.dto.request;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class CodeDto {
    @Max(4)
    private String code;
}
