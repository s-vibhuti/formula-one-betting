package org.sportybet.formulaonebetting.services.external.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sportybet.formulaonebetting.model.external.OpenF1DriverDto;
import org.sportybet.formulaonebetting.model.external.OpenF1SessionDto;
import org.sportybet.formulaonebetting.model.response.DriverMarketDto;
import org.sportybet.formulaonebetting.model.response.SessionResponseDto;
import org.sportybet.formulaonebetting.services.external.OpenF1Service;
import org.sportybet.formulaonebetting.thirdpartyclient.OpenF1Client;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenF1ServiceImpl implements OpenF1Service {

    private final OpenF1Client openF1Client;

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Adjustable

    @Override
    public String getVendorKey() {
        return "openf1";
    }

    @Override
    public List<SessionResponseDto> fetchSessions(String sessionType, String country, Integer year) {
        // Fetch all sessions from external API
        List<OpenF1SessionDto> sessions = openF1Client.getSessions(sessionType, country, year);

        // For each session, fetch drivers concurrently
        List<CompletableFuture<SessionResponseDto>> futures = sessions.stream()
                .map(session -> CompletableFuture.supplyAsync(() -> {
                    List<OpenF1DriverDto> drivers = openF1Client.getDriversBySessionKey(session.getSession_key());

                    // Map all drivers to DriverMarketDto list
                    List<DriverMarketDto> driverMarkets = drivers.stream()
                            .map(driver -> DriverMarketDto.builder()
                                    .fullName(driver.getFull_name())
                                    .driverNumber(driver.getDriver_number())
                                    .odds(getRandomOdds())
                                    .build())
                            .collect(Collectors.toList());

                    return SessionResponseDto.builder()
                            .sessionName(session.getSession_name())
                            .sessionType(session.getSession_type())
                            .countryName(session.getCountry_name())
                            .year(session.getYear())
                            .dateStart(session.getDate_start())
                            .dateEnd(session.getDate_end())
                            .sessionKey(session.getSession_key())
                            .circuitName(session.getCircuit_short_name())
                            .driverMarket(driverMarkets) // Set all drivers
                            .build();
                }, executor)).collect(Collectors.toList());

        // Join all futures and collect result
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private int getRandomOdds() {
        List<Integer> odds = Arrays.asList(2, 3, 4);
        return odds.get(new Random().nextInt(odds.size()));
    }
}
