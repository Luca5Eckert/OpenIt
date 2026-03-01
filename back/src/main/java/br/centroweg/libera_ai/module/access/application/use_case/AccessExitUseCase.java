package br.centroweg.libera_ai.module.access.application.use_case;

import br.centroweg.libera_ai.module.access.domain.exception.PaymentRequiredException;
import br.centroweg.libera_ai.module.access.domain.port.PaymentValidator;
import br.centroweg.libera_ai.module.access.presentation.dto.AccessExitRequest;
import br.centroweg.libera_ai.module.access.domain.event.ExitAccessEvent;
import br.centroweg.libera_ai.module.access.domain.exception.AccessCodeNotValidException;
import br.centroweg.libera_ai.module.access.domain.model.Access;
import br.centroweg.libera_ai.module.access.domain.port.AccessRepository;
import br.centroweg.libera_ai.module.access.domain.port.ExitEventProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccessExitUseCase {

    private final AccessRepository accessRepository;

    private final ExitEventProducer eventProducer;
    private final PaymentValidator paymentValidator;

    public AccessExitUseCase(AccessRepository accessRepository, ExitEventProducer eventProducer, PaymentValidator paymentValidator) {
        this.accessRepository = accessRepository;
        this.eventProducer = eventProducer;
        this.paymentValidator = paymentValidator;
    }

    @Transactional
    public Access execute(AccessExitRequest accessExitRequest) {
        Access access = accessRepository.findByCodeAndExitIsNull(accessExitRequest.code())
                .orElseThrow(() -> new AccessCodeNotValidException("Access code not valid"));

        if(!paymentValidator.isPaymentValid(access.getCode())){
            throw new PaymentRequiredException("Payment not confirmed for this code");
        }

        access.addExit();
        accessRepository.save(access);

        var event = ExitAccessEvent.of(access.getCode());
        eventProducer.send(event);

        return access;
    }

}
