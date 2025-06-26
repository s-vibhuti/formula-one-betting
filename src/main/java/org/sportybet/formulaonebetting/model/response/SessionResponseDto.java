package org.sportybet.formulaonebetting.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SessionResponseDto {

    private String sessionName;
    private String sessionType;
    private String countryName;
    private int year;
    private String dateStart;
    private String dateEnd;
    private int sessionKey;
    private String circuitName;
    private List<DriverMarketDto> driverMarket;
}
