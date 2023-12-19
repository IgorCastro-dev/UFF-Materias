package com.tcc.uffmaterias.domain.service;
import com.tcc.uffmaterias.domain.model.UsuarioTipo;
import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.domain.repository.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.UsuarioTipoRepository;
import com.tcc.uffmaterias.dto.request.UsuarioRequestDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioTipoRepository usuarioTipoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Usuarios> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public void deletaUsuario(Long id){
        getUsuarios(id);
        usuarioRepository.deleteById(id);
    }

    public Usuarios buscaUsuario(Long id) {
        return getUsuarios(id);
    }

    private Usuarios getUsuarios(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public Usuarios atualizarUsuario(Long id, UsuarioRequestDto usuarioRequestDto) {
        Usuarios usuarios = getUsuarios(id);
        modelMapper.map(usuarioRequestDto,usuarios);
        UsuarioTipo usuarioTipo = usuarioTipoRepository.findByNome(usuarioRequestDto.getUsuarioTipo())
                .orElseThrow(()->new NotFoundException("Tipo de usuário não encontrado"));
        usuarios.setUsuarioTipo(usuarioTipo);
        usuarioRepository.save(usuarios);
        return usuarios;
    }
}
