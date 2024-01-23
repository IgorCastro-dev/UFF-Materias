package com.tcc.uffmaterias.domain.service;
import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import com.tcc.uffmaterias.domain.model.jpa.Usuarios;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioRepository;
import com.tcc.uffmaterias.domain.repository.jpa.UsuarioTipoRepository;
import com.tcc.uffmaterias.dto.request.UsuarioRequestDto;
import com.tcc.uffmaterias.error.erros.NotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("Usuário não encontrado")
        );
    }

    public Usuarios buscaUsuario(Long id) {
        return getUsuarios(id);
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

    private Usuarios getUsuarios(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }


}
