package com.tcc.uffmaterias.dto.response;

import lombok.Data;

@Data
public class ConteudoSecaoResponseDto {
    private Long conteudoSecaoId;
    private String conteudoDaSecao;
    private String descricao;
    private String url;
    private SecaoMateriaResponseDto secaoMaterias;
}
