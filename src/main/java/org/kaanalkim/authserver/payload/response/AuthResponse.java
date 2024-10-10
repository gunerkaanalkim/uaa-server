package org.kaanalkim.authserver.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;

    private UserDetails userDetails;
}