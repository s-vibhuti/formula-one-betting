package org.sportybet.formulaonebetting.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sportybet.formulaonebetting.entities.Bet;
import org.sportybet.formulaonebetting.entities.User;
import org.sportybet.formulaonebetting.enums.BetStatus;
import org.sportybet.formulaonebetting.exceptions.BetProcessingException;
import org.sportybet.formulaonebetting.model.EventOutcomePayload;
import org.sportybet.formulaonebetting.repositories.BetRepository;
import org.sportybet.formulaonebetting.repositories.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOutcomeConsumer {

    private final ObjectMapper objectMapper;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    @RabbitListener(queues = "${rabbitmq.queue.eventOutcome}")
    @Transactional
    public void consumeOutcome(String rawMessage) {
        try {
            EventOutcomePayload outcome = objectMapper.readValue(rawMessage, EventOutcomePayload.class);
            log.info("Processing outcome for sessionKey={}, winner={}", outcome.getSessionKey(), outcome.getWinningDriverNumber());

            List<Bet> bets = betRepository.findAllBySessionKey(outcome.getSessionKey());

            if (bets.isEmpty()) {
                throw new BetProcessingException("No bets found for session: " + outcome.getSessionKey());
            }

            for (Bet bet : bets) {
                if (bet.getDriverNumber().equals(outcome.getWinningDriverNumber())) {
                    bet.setStatus(BetStatus.WON);
                    BigDecimal winnings = bet.getAmount().multiply(BigDecimal.valueOf(bet.getOdds()));
                    User user = bet.getUser();
                    user.setBalance(user.getBalance().add(winnings));
                } else {
                    bet.setStatus(BetStatus.LOST);
                }
            }

            // Save updated bets and users
            betRepository.saveAll(bets);
            userRepository.saveAll(bets.stream()
                    .map(Bet::getUser)
                    .distinct()
                    .toList());

            log.info("Outcome processed successfully for session {}", outcome.getSessionKey());

        } catch (Exception ex) {
            log.error("Failed to process event outcome message", ex);
            throw new BetProcessingException("Failed to process outcome: " + ex.getMessage(), ex);
        }
    }

}
