package com.tcc.uffmaterias.evento.event;

import org.springframework.context.ApplicationEvent;

public class CadastroEvent extends ApplicationEvent {

    private String email;
    private String code;
    public CadastroEvent(Object source,String email,String code) {
        super(source);
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
