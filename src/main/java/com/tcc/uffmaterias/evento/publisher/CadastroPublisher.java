package com.tcc.uffmaterias.evento.publisher;

import com.tcc.uffmaterias.evento.event.CadastroEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CadastroPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public CadastroPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publicarEvento(String email, String code) {
        CadastroEvent cadastroEvent = new CadastroEvent(this, email, code);
        eventPublisher.publishEvent(cadastroEvent);
    }
}
