package org.sportybet.formulaonebetting.services;

import org.sportybet.formulaonebetting.model.response.SessionResponseDto;

import java.util.List;

public interface SessionService {
    List<SessionResponseDto> getSessions(String country, String sessionType, Integer year);
}
