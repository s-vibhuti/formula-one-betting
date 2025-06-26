package org.sportybet.formulaonebetting.model.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AuthRequest {
    private String userId;
    private String password;
}
