
package org.kaanalkim.authserver.payload.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String username;
    private Integer realmId;
}
