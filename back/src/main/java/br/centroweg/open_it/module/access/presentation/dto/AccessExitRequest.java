package br.centroweg.open_it.module.access.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AccessExitRequest(
        @NotNull Integer code
) {
}
