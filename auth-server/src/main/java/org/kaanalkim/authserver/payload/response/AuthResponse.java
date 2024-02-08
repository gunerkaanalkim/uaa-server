package org.kaanalkim.authserver.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;

    private UserInfo userInfo;
}