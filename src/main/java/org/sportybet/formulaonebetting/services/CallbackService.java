package org.sportybet.formulaonebetting.services;

public interface CallbackService {
    void handleCallback(String rawPayload, String apiKey);
}
