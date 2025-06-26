package org.sportybet.formulaonebetting.services;

import org.sportybet.formulaonebetting.model.request.PlaceBetRequest;
import org.sportybet.formulaonebetting.model.response.PlaceBetResponse;

import java.util.List;

public interface BettingService {

    public List<PlaceBetResponse> placeBet(PlaceBetRequest request, String userIdFromJwt);
}
