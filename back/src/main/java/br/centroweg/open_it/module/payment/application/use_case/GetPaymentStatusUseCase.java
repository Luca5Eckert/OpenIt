package br.centroweg.open_it.module.payment.application.use_case;

import br.centroweg.open_it.module.payment.domain.model.Payment;
import br.centroweg.open_it.module.payment.domain.port.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetPaymentStatusUseCase {

    private final PaymentRepository paymentRepository;

    public GetPaymentStatusUseCase(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Transactional(readOnly = true)
    public boolean execute(String externalPaymentId) {
        return paymentRepository.findByExternalId(externalPaymentId)
                .map(Payment::isPaid)
                .orElse(false);
    }

}