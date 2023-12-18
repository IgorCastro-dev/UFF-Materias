package com.tcc.uffmaterias.domain.service;
import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.domain.repository.UsuarioRepository;
import com.tcc.uffmaterias.error.erros.NotFoundException;
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

    public void deletaUsuario(Long id){
        usuarioRepository.findById(id).orElseThrow(()-> new NotFoundException("Usuário não encontrado"));
        usuarioRepository.deleteById(id);
    }
}
