package com.tcc.uffmaterias.error;

import lombok.Getter;

@Getter
public enum ErroType {
    RECURSO_NAO_ENCONTRADA("Recurso não Encontrado"),
    ENTIDADE_EM_USO("Entidade em uso"),
    ERRO_NEGOCIO("Violação de regra de negócio"),
    PARAMETRO_INVALIDO("Parametro inválido"),
    MENSAGEM_INCOMPREENSIVEL( "Mensagem incompreensível"),
    DADOS_INVALIDDOS("dados invalidos"),
    ERRO_DE_SISTEMA("Erro de sistema");

    private String title;

    ErroType(String title){
        this.title = title;
    }
}
