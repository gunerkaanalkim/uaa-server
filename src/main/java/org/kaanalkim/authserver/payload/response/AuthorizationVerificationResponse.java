package org.kaanalkim.authserver.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationVerificationResponse {
    private boolean hasPermission;
}
