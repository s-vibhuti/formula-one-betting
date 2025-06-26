package org.sportybet.formulaonebetting.exceptions;

public class ConfigNotFoundException extends RuntimeException {
    public ConfigNotFoundException(String key) {
        super("Configuration value not found for key: " + key);
    }
}
