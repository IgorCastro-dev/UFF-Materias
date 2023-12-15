package com.tcc.uffmaterias.domain.service;
import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }
}
