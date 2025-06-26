package org.sportybet.formulaonebetting.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDetailsResponse {
    private String userId;
    private BigDecimal balance;
}