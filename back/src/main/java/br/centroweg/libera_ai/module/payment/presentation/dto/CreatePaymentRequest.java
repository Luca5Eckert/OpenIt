package br.centroweg.libera_ai.module.payment.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull Integer accessCode
) {
}
