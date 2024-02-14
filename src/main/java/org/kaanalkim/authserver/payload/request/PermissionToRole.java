package org.kaanalkim.authserver.payload.request;

import lombok.Data;

@Data
public class PermissionToRole {
    private long permissionId;
    private long roleId;
}
