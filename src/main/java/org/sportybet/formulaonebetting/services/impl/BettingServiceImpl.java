package org.sportybet.formulaonebetting.services.impl;

import org.sportybet.formulaonebetting.entities.Bet;
import org.sportybet.formulaonebetting.entities.User;
import org.sportybet.formulaonebetting.enums.BetStatus;
import org.sportybet.formulaonebetting.exceptions.InsufficientBalanceException;
import org.sportybet.formulaonebetting.model.request.PlaceBetRequest;
import org.sportybet.formulaonebetting.model.response.PlaceBetResponse;
import org.sportybet.formulaonebetting.repositories.BetRepository;
import org.sportybet.formulaonebetting.repositories.ConfigRepository;
import org.sportybet.formulaonebetting.repositories.UserRepository;
import org.sportybet.formulaonebetting.services.BettingService;
import org.sportybet.formulaonebetting.services.factory.SessionClientFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BettingServiceImpl implements BettingService {


    private final UserRepository userRepository;
    private final BetRepository betRepository;

    public BettingServiceImpl(UserRepository userRepository, BetRepository betRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
    }

    public List<PlaceBetResponse> placeBet(PlaceBetRequest request, String userIdFromJwt) {
        // 1. Fetch user
        User user = userRepository.findById(userIdFromJwt)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check balance
        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(user.getBalance());
        }

        // 3. Deduct balance
        user.setBalance(user.getBalance().subtract(request.getAmount()));
        userRepository.save(user);

        // 4. Place bet
        betRepository.save(Bet.builder()
                .user(user)
                .sessionKey(request.getSessionKey())
                .driverNumber(request.getDriverNumber())
                .amount(request.getAmount())
                .odds(request.getOdds())
                .status(BetStatus.PLACED)
                .build());

        // 5. Return all active bets (PLACED) for user
        List<Bet> activeBets = betRepository.findAllByUserUserIdAndStatus(user.getUserId(), BetStatus.PLACED);

        return activeBets.stream()
                .map(b -> PlaceBetResponse.builder()
                        .betId(b.getId())
                        .sessionKey(b.getSessionKey())
                        .driverNumber(b.getDriverNumber())
                        .amount(b.getAmount())
                        .odds(b.getOdds())
                        .status(b.getStatus().name())
                        .build())
                .toList();
    }
}
