package com.tcc.uffmaterias.domain.repository;

import com.tcc.uffmaterias.domain.model.ConteudoSecao;
import com.tcc.uffmaterias.domain.model.Materias;
import com.tcc.uffmaterias.domain.model.SecaoMaterias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConteudoSecaoRepository extends JpaRepository<ConteudoSecao,Long> {
    Optional<ConteudoSecao> findByNome(String fileNome);
    List<ConteudoSecao> findAllBySecaoMaterias(SecaoMaterias secaoMaterias);
}
