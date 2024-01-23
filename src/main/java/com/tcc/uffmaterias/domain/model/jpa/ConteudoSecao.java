package com.tcc.uffmaterias.domain.model.jpa;

import com.tcc.uffmaterias.domain.model.jpa.SecaoMaterias;
import jakarta.persistence.Column;
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
    @Column(unique = true)
    private String nome;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "secao_materias_id")
    private SecaoMaterias secaoMaterias;
}
