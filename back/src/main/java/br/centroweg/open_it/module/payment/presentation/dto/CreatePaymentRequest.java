package br.centroweg.open_it.module.payment.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull Integer accessCode
) {
}
