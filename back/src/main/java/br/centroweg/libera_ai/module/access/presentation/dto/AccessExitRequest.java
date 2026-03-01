package br.centroweg.libera_ai.module.access.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AccessExitRequest(
        @NotNull Integer code
) {
}
