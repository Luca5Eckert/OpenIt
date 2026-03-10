package br.centroweg.open_it.module.payment.domain.model;

public record PaymentInfo(
        String generatedPaymentId,
        String linkPayment,
        double amount
) {
}
