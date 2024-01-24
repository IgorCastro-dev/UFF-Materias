package com.tcc.uffmaterias.evento.publisher;

import com.tcc.uffmaterias.evento.event.RecoveryEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RecoveryPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public RecoveryPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publicarEvento(String email, String code) {
        RecoveryEvent recoveryEvent = new RecoveryEvent(this, email, code);
        eventPublisher.publishEvent(recoveryEvent);
    }
}
