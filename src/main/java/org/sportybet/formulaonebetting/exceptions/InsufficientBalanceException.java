package org.sportybet.formulaonebetting.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal amount) {
        super("Insufficient balance to place bet of amount: " + amount);
    }
}
