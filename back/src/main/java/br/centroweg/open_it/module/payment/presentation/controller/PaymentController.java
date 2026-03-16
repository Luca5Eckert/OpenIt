package br.centroweg.open_it.module.payment.presentation.controller;

import br.centroweg.open_it.module.payment.application.use_case.ProcessPaymentNotificationUseCase;
import br.centroweg.open_it.module.payment.presentation.dto.CreatePaymentRequest;
import br.centroweg.open_it.module.payment.presentation.dto.MercadoPagoWebhookRequest;
import br.centroweg.open_it.module.payment.presentation.dto.PaymentResponse;
import br.centroweg.open_it.module.payment.presentation.mapper.PaymentMapper;
import br.centroweg.open_it.module.payment.application.use_case.CreatePaymentUseCase;
import br.centroweg.open_it.module.payment.application.use_case.GetPaymentStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/payments")
@Tag(
        name = "Payment",
        description = "Endpoints for managing payments, including creation, verification and notification handler"
)
public class PaymentController {

    private final CreatePaymentUseCase createUseCase;
    private final GetPaymentStatusUseCase statusUseCase;
    private final ProcessPaymentNotificationUseCase processNotificationUseCase;

    private final PaymentMapper mapper;

    public PaymentController(CreatePaymentUseCase createUseCase, GetPaymentStatusUseCase statusUseCase, ProcessPaymentNotificationUseCase processNotificationUseCase, PaymentMapper mapper) {
        this.createUseCase = createUseCase;
        this.statusUseCase = statusUseCase;
        this.processNotificationUseCase = processNotificationUseCase;
        this.mapper = mapper;
    }


    @PostMapping
    @Operation(
            summary = "Create a new payment given an access code",
            description = "Creates a new payment for the access code, return the payment info needed to complete the payment on the provider's side"
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentResponse.class)
            )
    )
    public ResponseEntity<PaymentResponse> create(@RequestBody @Valid CreatePaymentRequest request) {
        var paymentInfo = createUseCase.execute(request.accessCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toPaymentResponse(paymentInfo));
    }


    @GetMapping(path = "/stream/{paymentId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(
            summary = "Stream payment status updates",
            description = "Streams the payment status for the given payment ID, emitting updates every second until the payment is confirmed as paid"
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "text/event-stream",
                    schema = @Schema(implementation = String.class)
            )
    )
    public Flux<ServerSentEvent<String>> streamPaymentStatus(@PathVariable String paymentId) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> statusUseCase.execute(paymentId))
                .map(isPaid -> ServerSentEvent.<String>builder()
                        .data(String.valueOf(isPaid))
                        .event("payment-status")
                        .build())
                .takeUntil(sse -> "true".equals(sse.data()))
                .doOnError(e -> System.err.println("Erro no stream: " + e.getMessage()))
                .onErrorResume(e -> Flux.empty());
    }


    @PostMapping("/webhook")
    @Operation(
            summary = "Handle Mercado Pago payment notifications",
            description = """
                    Endpoint to receive payment notifications from Mercado Pago.
                    It processes the notification asynchronously to update the payment status in the system.
                    """
    )
    @ApiResponse(
            responseCode = "200"
    )
    public ResponseEntity<Void> webhook(@RequestBody MercadoPagoWebhookRequest request) {
        if ("payment".equals(request.type()) && request.data() != null) {
            CompletableFuture.runAsync(() -> processNotificationUseCase.execute(request.data().paymentId()));
        }
        return ResponseEntity.ok().build();
    }

}
