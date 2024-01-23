package com.tcc.uffmaterias.domain.repository.jpa;

import com.tcc.uffmaterias.domain.model.jpa.ConteudoSecao;
import com.tcc.uffmaterias.domain.model.jpa.SecaoMaterias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConteudoSecaoRepository extends JpaRepository<ConteudoSecao,Long> {
    Optional<ConteudoSecao> findByNome(String fileNome);
    List<ConteudoSecao> findAllBySecaoMaterias(SecaoMaterias secaoMaterias);
}
