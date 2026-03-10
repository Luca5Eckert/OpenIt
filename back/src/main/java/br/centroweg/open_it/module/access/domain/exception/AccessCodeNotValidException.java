package br.centroweg.open_it.module.access.domain.exception;

public class AccessCodeNotValidException extends AccessDomainException {
    public AccessCodeNotValidException(String message) {
        super(message);
    }

    public AccessCodeNotValidException() {
        super("Access code is not valid.");
    }
}
