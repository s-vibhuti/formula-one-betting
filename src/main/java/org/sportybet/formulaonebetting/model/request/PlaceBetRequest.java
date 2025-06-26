package org.sportybet.formulaonebetting.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceBetRequest {
    private Integer sessionKey;
    private Integer driverNumber;
    private BigDecimal amount;
    private Integer odds; // ✅ added field
}
