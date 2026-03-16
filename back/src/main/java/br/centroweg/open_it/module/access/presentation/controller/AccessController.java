package br.centroweg.open_it.module.access.presentation.controller;

import br.centroweg.open_it.module.access.presentation.dto.AccessExitRequest;
import br.centroweg.open_it.module.access.presentation.dto.AccessExitResponse;
import br.centroweg.open_it.module.access.presentation.mapper.AccessMapper;
import br.centroweg.open_it.module.access.application.use_case.AccessExitUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
@Tag(
        name = "Access",
        description = "Endpoints related to access control, including exit management."
)
public class AccessController {

    private final AccessMapper mapper;

    private final AccessExitUseCase accessExitUseCase;

    public AccessController(AccessMapper mapper, AccessExitUseCase accessExitUseCase) {
        this.mapper = mapper;
        this.accessExitUseCase = accessExitUseCase;
    }

    @PutMapping("/exit")
    @Operation(
            summary = "Register an exit for a given access code",
            description = "This endpoint allows you to register an exist for a specified access code. It validates the access code, and only open if is valid"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccessExitResponse.class)
            )
    )
    public ResponseEntity<AccessExitResponse> exit(
            @RequestBody @Valid AccessExitRequest request
    ) {
        var access = accessExitUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.toResponse(access));
    }

}
