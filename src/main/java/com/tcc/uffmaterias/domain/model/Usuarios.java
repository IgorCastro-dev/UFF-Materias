package com.tcc.uffmaterias.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuariosId;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String senha;
    @Column(nullable = false)
    private String celular;
    @ManyToOne
    @JoinColumn(name = "usuario_tipo_id")
    private UsuarioTipo usuarioTipo;
}
