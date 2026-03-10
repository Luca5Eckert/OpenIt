package br.centroweg.open_it.module.payment.domain.exception;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
