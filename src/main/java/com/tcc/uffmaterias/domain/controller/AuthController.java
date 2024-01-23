package com.tcc.uffmaterias.domain.controller;

import com.tcc.uffmaterias.domain.service.AuthenticationService;
import com.tcc.uffmaterias.dto.request.LoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDTO){
        return ResponseEntity.ok(authenticationService.autenticate(loginDTO));
    }

}
