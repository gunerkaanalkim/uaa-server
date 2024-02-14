package org.kaanalkim.authserver.payload.request;

import lombok.Data;

@Data
public class RoleToUser {
    private Long roleId;
    private Long userId;
}
