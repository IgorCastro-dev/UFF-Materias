package com.tcc.uffmaterias.domain.repository.jpa;

import com.tcc.uffmaterias.domain.model.jpa.Materias;
import com.tcc.uffmaterias.domain.model.jpa.SecaoMaterias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecaoMateriaRepository extends JpaRepository<SecaoMaterias,Long> {

    List<SecaoMaterias> findAllByMateria(Materias materias);
}
