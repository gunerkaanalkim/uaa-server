package org.kaanalkim.authserver.payload.response;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTVerificationResponse {
    private Boolean isValid;
    private Claims claims;
}
