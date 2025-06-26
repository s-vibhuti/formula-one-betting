package org.sportybet.formulaonebetting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    private BigDecimal balance = BigDecimal.valueOf(100.00);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bet> bets = new ArrayList<>();
}