package org.sportybet.formulaonebetting.model;

import lombok.Data;

@Data
public class EventOutcomePayload {
    private Integer sessionKey;
    private Integer winningDriverNumber;
}