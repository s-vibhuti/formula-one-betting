package org.sportybet.formulaonebetting.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User with ID '" + userId + "' not found");
    }
}

