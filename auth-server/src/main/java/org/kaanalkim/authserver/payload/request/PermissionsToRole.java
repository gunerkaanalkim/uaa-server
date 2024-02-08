package org.kaanalkim.authserver.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class PermissionsToRole {
    private List<Long> permissionIds;
    private Long roleId;
}
