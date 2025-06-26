package org.sportybet.formulaonebetting.services.impl;

import org.sportybet.formulaonebetting.entities.CallbackEventPayload;
import org.sportybet.formulaonebetting.publisher.FormulaOneEventOutcomPublisher;
import org.sportybet.formulaonebetting.repositories.CallbackEventPayloadRepository;

import org.sportybet.formulaonebetting.services.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CallbackServiceImpl implements CallbackService {

    @Value("${openf1.api.key}")
    private String openF1ApiKey;

    @Autowired
    private CallbackEventPayloadRepository payloadRepository;

    @Autowired
    private FormulaOneEventOutcomPublisher publisher;

    @Override
    public void handleCallback(String rawPayload, String apiKey) {
        if (!openF1ApiKey.equals(apiKey)) {
            throw new SecurityException("Invalid API Key");
        }

        CallbackEventPayload event = CallbackEventPayload.builder()
                .rawPayload(rawPayload)
                .receivedAt(Instant.now())
                .build();

        payloadRepository.save(event);
        publisher.publish(rawPayload);
    }
}