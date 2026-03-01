package br.centroweg.libera_ai.module.access.domain.port;

public interface PaymentValidator {

    boolean isPaymentValid(int code);

}
