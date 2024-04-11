package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.exception.UserDisabledException;
import org.kaanalkim.authserver.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    @Override
    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException(String.format("%s %s", "USER_DISABLED", e.getMessage()));
        } catch (BadCredentialsException e) {
            throw new UserDisabledException(String.format("%s %s", "INVALID_CREDENTIALS", e.getMessage()));
        }
    }
}
