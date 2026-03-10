package br.centroweg.open_it.module.payment.domain.port;

import br.centroweg.open_it.module.payment.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findById(String paymentId);

    Optional<Payment> findByAccessCode(int code);

    Optional<Payment> findByExternalId(String externalId);
}
