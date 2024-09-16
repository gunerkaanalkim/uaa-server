
package org.kaanalkim.authserver.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ForgotPasswordResponse {
    private String message;
    private Date timeout;
}
