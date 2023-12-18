package com.tcc.uffmaterias.domain.error.erros;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
