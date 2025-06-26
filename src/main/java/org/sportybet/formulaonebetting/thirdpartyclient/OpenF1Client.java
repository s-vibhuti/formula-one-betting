package org.sportybet.formulaonebetting.thirdpartyclient;

import org.sportybet.formulaonebetting.model.external.OpenF1DriverDto;
import org.sportybet.formulaonebetting.model.external.OpenF1SessionDto;

import java.util.List;

public interface OpenF1Client {
    List<OpenF1SessionDto> getSessions(String country, String sessionName, Integer year);
    List<OpenF1DriverDto> getDriversBySessionKey(int sessionKey);
}
