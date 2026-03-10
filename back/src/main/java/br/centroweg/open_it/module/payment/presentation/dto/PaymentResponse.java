package br.centroweg.open_it.module.payment.presentation.dto;

public record PaymentResponse(
        double amount,
        String linkPayment,
        String paymentId
) {
}
