package com.tcc.uffmaterias.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuariosId;
    private String nome;
    private String email;
    private String senha;
    private String celular;
    @ManyToOne
    @JoinColumn(name = "usuario_tipo_id")
    private UsuarioTipo usuarioTipo;
}
