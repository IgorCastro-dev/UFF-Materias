package com.tcc.uffmaterias.domain.model.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SecaoMaterias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secaoMateriasId;
    private String secaoMateriasNome;

    @ManyToOne
    @JoinColumn(name = "materias_id")
    private Materias materia;

    @OneToMany(mappedBy = "secaoMaterias", cascade = CascadeType.ALL)
    private List<ConteudoSecao> conteudosSecao;
}
