package com.tcc.uffmaterias.domain.repository;

import com.tcc.uffmaterias.domain.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {
}
