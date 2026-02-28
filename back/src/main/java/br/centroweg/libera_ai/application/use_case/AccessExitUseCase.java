package br.centroweg.libera_ai.application.use_case;

import br.centroweg.libera_ai.application.dto.AccessExitRequest;
import br.centroweg.libera_ai.domain.event.ExitAccessEvent;
import br.centroweg.libera_ai.domain.exception.AccessCodeNotValidException;
import br.centroweg.libera_ai.domain.model.Access;
import br.centroweg.libera_ai.domain.port.AccessRepository;
import br.centroweg.libera_ai.domain.port.ExitEventProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccessExitUseCase {

    private final AccessRepository accessRepository;
    private final ExitEventProducer eventProducer;

    public AccessExitUseCase(AccessRepository accessRepository, ExitEventProducer eventProducer) {
        this.accessRepository = accessRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public Access execute(AccessExitRequest accessExitRequest) {
        Access access = accessRepository.findByCodeAndExitIsNull(accessExitRequest.code())
                .orElseThrow(AccessCodeNotValidException::new);

        access.addExit();

        accessRepository.save(access);

        var event = ExitAccessEvent.of(access.getCode());

        eventProducer.send(event);

        return access;
    }

}
