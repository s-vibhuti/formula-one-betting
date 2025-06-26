package org.sportybet.formulaonebetting.repositories;

import org.sportybet.formulaonebetting.entities.Bet;
import org.sportybet.formulaonebetting.enums.BetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findAllByUserUserIdAndStatus(String userId, BetStatus status);

    List<Bet> findAllBySessionKeyAndStatus(Integer sessionKey, BetStatus placed);
}