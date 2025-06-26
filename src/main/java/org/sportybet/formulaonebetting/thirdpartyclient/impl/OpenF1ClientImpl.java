package org.sportybet.formulaonebetting.thirdpartyclient.impl;

import lombok.RequiredArgsConstructor;
import org.sportybet.formulaonebetting.model.external.OpenF1DriverDto;
import org.sportybet.formulaonebetting.model.external.OpenF1SessionDto;
import org.sportybet.formulaonebetting.repositories.ConfigRepository;
import org.sportybet.formulaonebetting.services.impl.ConfigService;
import org.sportybet.formulaonebetting.thirdpartyclient.OpenF1Client;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Component
public class OpenF1ClientImpl implements OpenF1Client {

    private final RestTemplate restTemplate;
    private final ConfigService configService;

    public OpenF1ClientImpl(RestTemplate restTemplate, ConfigService configService) {
        this.restTemplate = restTemplate;
        this.configService = configService;
    }

    @Override
    public List<OpenF1SessionDto> getSessions(String country, String sessionName, Integer year) {
        String baseUrl = configService.getConfigValue("openf1.sessions.url");

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParamIfPresent("country_name", Optional.ofNullable(country))
                .queryParamIfPresent("session_name", Optional.ofNullable(sessionName))
                .queryParamIfPresent("year", Optional.ofNullable(year))
                .toUriString();
        ResponseEntity<List<OpenF1SessionDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    @Override
    public List<OpenF1DriverDto> getDriversBySessionKey(int sessionKey) {
        String baseUrl = configService.getConfigValue("openf1.drivers.url");

        String url = baseUrl + "?session_key=" + sessionKey;

        ResponseEntity<List<OpenF1DriverDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }
}
