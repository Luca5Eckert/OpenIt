package br.centroweg.open_it.module.payment.infrastructure.persistence.repository;

import br.centroweg.open_it.module.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentEntityRepositoryJpa extends JpaRepository<PaymentEntity, String> {


    Optional<PaymentEntity> findByAccessCode(int code);

    Optional<PaymentEntity> findByExternalId(String externalId);
}
