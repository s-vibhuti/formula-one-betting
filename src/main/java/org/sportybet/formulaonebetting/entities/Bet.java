package org.sportybet.formulaonebetting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sportybet.formulaonebetting.enums.BetStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "bets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private Integer sessionKey;

    private Integer driverNumber;

    private BigDecimal amount;

    private Integer odds;

    @Enumerated(EnumType.STRING)
    private BetStatus status;

    private Timestamp placedAt;


    @PrePersist
    protected void onCreate() {
        this.placedAt = new Timestamp(System.currentTimeMillis());
    }
}
