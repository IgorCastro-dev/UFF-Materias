package com.tcc.uffmaterias.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ConteudoSecao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conteudoSecaoId;
    private String conteudoDaSecao;
    @ManyToOne
    @JoinColumn(name = "secao_materias_id")
    private SecaoMaterias secaoMaterias;
}
