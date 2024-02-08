package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RolePermissionDTO extends AbstractDTO {
    protected Long id;
    private Permission permission;
    private Role role;
}
