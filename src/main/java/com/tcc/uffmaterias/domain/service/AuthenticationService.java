package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.dto.request.LoginDto;
import com.tcc.uffmaterias.error.erros.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public Token autenticate(LoginDto loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            var auth = authenticationManager.authenticate(authenticationToken);
            Usuarios userCredentials = (Usuarios) auth.getPrincipal();
            return tokenService.generateToken(userCredentials);
        }catch (BadCredentialsException e){
            throw new UnauthorizedException(e.getMessage(),e);
        }

    }
}
