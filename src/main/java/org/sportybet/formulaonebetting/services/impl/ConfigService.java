package org.sportybet.formulaonebetting.services.impl;

import org.sportybet.formulaonebetting.entities.Config;
import org.sportybet.formulaonebetting.exceptions.ConfigNotFoundException;
import org.sportybet.formulaonebetting.repositories.ConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public String getConfigValue(String configKey) {
        return configRepository.findByConfigKey(configKey)
                .map(Config::getConfigValue)
                .orElseThrow(() -> new ConfigNotFoundException(configKey));
    }
}
