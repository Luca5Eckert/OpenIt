package br.centroweg.libera_ai.module.access.domain.exception;

public class PaymentRequiredException extends AccessDomainException {
    public PaymentRequiredException(String message) {
        super(message);
    }
}
