package com.tcc.uffmaterias.domain.repository;

import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecaoMateriaRepository extends JpaRepository<SecaoMaterias,Long> {

    List<SecaoMaterias> findAllByMateria(Materias materias);
}
