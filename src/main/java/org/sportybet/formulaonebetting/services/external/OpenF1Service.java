package org.sportybet.formulaonebetting.services.external;

import org.sportybet.formulaonebetting.model.response.SessionResponseDto;

import java.util.List;

public interface OpenF1Service {
    String getVendorKey(); // this identifies the client (e.g., "openf1")
    List<SessionResponseDto> fetchSessions(String country, String sessionType, Integer year);
}