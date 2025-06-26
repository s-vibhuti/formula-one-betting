package org.sportybet.formulaonebetting.services.impl;

import org.sportybet.formulaonebetting.entities.Config;
import org.sportybet.formulaonebetting.model.response.SessionResponseDto;
import org.sportybet.formulaonebetting.repositories.ConfigRepository;
import org.sportybet.formulaonebetting.services.SessionService;
import org.sportybet.formulaonebetting.services.factory.SessionClientFactory;
import org.sportybet.formulaonebetting.services.external.OpenF1Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private final ConfigRepository configRepository;
    private final SessionClientFactory clientFactory;

    public SessionServiceImpl(ConfigRepository configRepository, SessionClientFactory clientFactory) {
        this.configRepository = configRepository;
        this.clientFactory = clientFactory;
    }

    @Override
    public List<SessionResponseDto> getSessions(String country, String sessionType, Integer year) {
        // Get client key from config table
        String clientKey = configRepository.findByConfigKey("f1.client")
                .map(Config::getConfigValue)
                .orElseThrow(() -> new RuntimeException("Session client not configured"));

        // Get client dynamically
        OpenF1Service client = clientFactory.getClient(clientKey);

        return client.fetchSessions(country, sessionType, year);
    }
}
