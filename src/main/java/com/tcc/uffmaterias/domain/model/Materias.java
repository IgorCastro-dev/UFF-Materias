package com.tcc.uffmaterias.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Materias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materiasId;
    @Column(nullable = false)
    private String nomeMateria;
    @Column(unique = true,nullable = false)
    private String codigo;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
    private List<SecaoMaterias> secaoMaterias;
}
