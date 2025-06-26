package org.sportybet.formulaonebetting.exceptions;

public class BetProcessingException extends RuntimeException {
    public BetProcessingException(String message) {
        super(message);
    }

    public BetProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
