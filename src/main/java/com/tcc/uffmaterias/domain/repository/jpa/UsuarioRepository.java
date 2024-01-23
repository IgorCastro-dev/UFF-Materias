package com.tcc.uffmaterias.domain.repository.jpa;

import com.tcc.uffmaterias.domain.model.jpa.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {
    Optional<Usuarios> findByEmail(String email);
}
