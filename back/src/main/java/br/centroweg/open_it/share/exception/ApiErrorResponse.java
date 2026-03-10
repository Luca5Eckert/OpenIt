package br.centroweg.open_it.share.exception;

public record ApiErrorResponse(
        int status,
        String message,
        String details
) {

    public static ApiErrorResponse of(int status, String message, String details) {
        return new ApiErrorResponse(status, message, details);
    }
}
