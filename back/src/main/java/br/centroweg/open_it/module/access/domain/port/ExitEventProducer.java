package br.centroweg.open_it.module.access.domain.port;

import br.centroweg.open_it.module.access.domain.event.ExitAccessEvent;

public interface ExitEventProducer {

    void send(ExitAccessEvent event);

}
