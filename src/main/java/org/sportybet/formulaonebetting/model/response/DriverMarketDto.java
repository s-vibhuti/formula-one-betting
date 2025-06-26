package org.sportybet.formulaonebetting.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverMarketDto {
    private String fullName;
    private int driverNumber;
    private int odds;
}
