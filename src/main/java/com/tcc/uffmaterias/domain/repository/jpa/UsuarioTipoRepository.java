package com.tcc.uffmaterias.domain.repository.jpa;

import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioTipoRepository extends JpaRepository<UsuarioTipo,Long> {
    public Optional<UsuarioTipo> findByNome(String nome);
}
