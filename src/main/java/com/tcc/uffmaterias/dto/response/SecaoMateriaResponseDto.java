package com.tcc.uffmaterias.dto.response;

import lombok.Data;
@Data
public class SecaoMateriaResponseDto {
    private Long secaoMateriasId;
    private String secaoMateriasNome;
    private MateriaResponseDto materia;
}
