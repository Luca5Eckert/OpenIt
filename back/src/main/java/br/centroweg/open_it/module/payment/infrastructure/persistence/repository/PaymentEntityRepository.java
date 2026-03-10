package br.centroweg.open_it.module.payment.infrastructure.persistence.repository;

import br.centroweg.open_it.module.payment.domain.model.Payment;
import br.centroweg.open_it.module.payment.domain.port.PaymentRepository;
import br.centroweg.open_it.module.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentEntityRepository implements PaymentRepository {

    private final PaymentEntityRepositoryJpa paymentEntityRepositoryJpa;

    public PaymentEntityRepository(PaymentEntityRepositoryJpa paymentEntityRepositoryJpa) {
        this.paymentEntityRepositoryJpa = paymentEntityRepositoryJpa;
    }

    @Override
    public void save(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntity.of(payment);

        paymentEntityRepositoryJpa.save(paymentEntity);
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        var paymentEntity = paymentEntityRepositoryJpa.findById(paymentId);

        return paymentEntity.map(PaymentEntity::toDomain);
    }

    @Override
    public Optional<Payment> findByAccessCode(int code) {
        var paymentEntity = paymentEntityRepositoryJpa.findByAccessCode(code);

        return paymentEntity.map(PaymentEntity::toDomain);
    }

    @Override
    public Optional<Payment> findByExternalId(String externalId) {
        var paymentEntity = paymentEntityRepositoryJpa.findByExternalId(externalId);

        return paymentEntity.map(PaymentEntity::toDomain);
    }
}
