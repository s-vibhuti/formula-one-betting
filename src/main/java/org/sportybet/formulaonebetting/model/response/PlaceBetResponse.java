package org.sportybet.formulaonebetting.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PlaceBetResponse {
    private Long betId;
    private Integer sessionKey;
    private Integer driverNumber;
    private BigDecimal amount;
    private Integer odds;
    private String status; // e.g., "PLACED"
}
