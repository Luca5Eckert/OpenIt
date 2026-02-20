package br.centroweg.libera_ai.application.mapper;

import br.centroweg.libera_ai.application.dto.AccessExitResponse;
import br.centroweg.libera_ai.domain.model.Access;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AccessMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public AccessExitResponse toResponse(Access access) {
        if (access == null) return null;

        return new AccessExitResponse(
                access.getId(),
                access.getEntry() != null ? access.getEntry().format(FORMATTER) : null,
                access.getExit() != null ? access.getExit().format(FORMATTER) : null
        );
    }

}