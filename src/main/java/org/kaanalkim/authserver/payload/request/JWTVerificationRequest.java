package org.kaanalkim.authserver.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
public class JWTVerificationRequest {
    private String token;
}
