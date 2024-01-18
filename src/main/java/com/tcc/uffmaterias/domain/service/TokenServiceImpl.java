package com.tcc.uffmaterias.domain.service;

import com.tcc.uffmaterias.domain.model.Usuarios;
import com.tcc.uffmaterias.dto.request.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    public Token generateToken(Usuarios userCredentials) {
        Date today = new Date();
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .setSubject(userCredentials.getUsername())
                .claim("roles",userCredentials.getUsuarioTipo().getNome())
                .setIssuer("Token do app")
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime()+300000))
                .compact();
        return new TokenDto(jwt,today.getTime(),userCredentials.getUsername());

    }

    @Override
    public Token allocateToken(String extendedInformation) {
        return null;
    }

    public Token verifyToken(String key) {
        Claims claims = Jwts.parser()
                .setSigningKey("$2a$12$gas0FT8qIhvVeYunvLNz8eA2otC0VFCCvKIOiIbs7EISdrAMVlUY6")
                .parseClaimsJws(key)
                .getBody();
        return new TokenDto(key,claims.getIssuedAt().getTime(),claims.getSubject());
    }

}
