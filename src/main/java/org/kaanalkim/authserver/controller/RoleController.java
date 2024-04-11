package org.kaanalkim.authserver.controller;


import java.util.List;

import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController extends AbstractController<Role, RoleDTO> {
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;

    @GetMapping("/get-assigned-permissions/{roleId}")
    public ResponseEntity<List<Permission>> getAssignedPermissionOfRole(@PathVariable("roleId") Long roleId) {
        List<Permission> assignedPermissionOfRole = this.rolePermissionService.getAssignedPermissionsOfRole(roleId);
        return ResponseEntity.ok().body(assignedPermissionOfRole);
    }


    @PostMapping("assign-permission")
    public ResponseEntity<RolePermission> assignPermissionToRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.assignPermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }


    @PostMapping("assign-all-permissions")
    public ResponseEntity<List<RolePermission>> assignAllPermissionToRole(@RequestBody PermissionsToRole permissionsToRole) {
        List<RolePermission> rolePermissions = this.rolePermissionService.assignAllPermissionToRole(permissionsToRole);
        return ResponseEntity.ok().body(rolePermissions);
    }


    @PostMapping("revoke-permission")
    public ResponseEntity<RolePermission> revokePermissionFromRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.revokePermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }

    @PostMapping("revoke-all-permissions")
    public ResponseEntity<Role> revokeAllPermissionFromRole(@RequestBody PermissionsToRole permissionsToRole) {
        Role role = this.rolePermissionService.revokeAllPermissionFromRole(permissionsToRole);
        return ResponseEntity.ok().body(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RoleService getService() {
        return this.roleService;
    }

}
