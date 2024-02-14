package org.kaanalkim.authserver.service;


public interface AuthenticationService {
    void authenticate(String username, String password) throws Exception;
}
