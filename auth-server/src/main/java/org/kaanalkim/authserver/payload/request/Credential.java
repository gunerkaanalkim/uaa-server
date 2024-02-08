package org.kaanalkim.authserver.payload.request;

import lombok.Data;

@Data
public class Credential {
    private String username;
    private String password;
}