package com.tcc.uffmaterias.domain.repository;

import com.tcc.uffmaterias.domain.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {
    Optional<Usuarios> findByEmail(String email);
}
