package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.dto.RolePermissionDTO;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
import org.kaanalkim.authserver.service.base.BaseCrudService;

import java.util.List;
import java.util.Optional;

public interface RolePermissionService extends BaseCrudService<RolePermission, RolePermissionDTO> {
    RolePermission assignPermissionToRole(PermissionToRole permissionToRole);
    RolePermission revokePermissionToRole(PermissionToRole permissionToRole);
    Role revokeAllPermissionFromRole(PermissionsToRole permissionsToRole);
    List<RolePermission> assignAllPermissionToRole(PermissionsToRole permissionsToRole);
    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
    List<Permission> getAssignedPermissionsOfRole(Long roleId);
}
