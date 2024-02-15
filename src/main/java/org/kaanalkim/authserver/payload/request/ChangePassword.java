package org.kaanalkim.authserver.payload.request;

import lombok.Data;

@Data
public class ChangePassword {
    private String password;
    private String rePassword;
}
