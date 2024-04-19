package org.kaanalkim.authserver.payload.response;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTVerificationResponse {
    private Boolean isValid;
    private Claims claims;
}
