package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController extends AbstractController<Role, RoleDTO> {
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;


    @Override
    @SuppressWarnings("unchecked")
    protected RoleService getService() {
        return this.roleService;
    }


    @GetMapping("/get-assigned-permissions/{roleId}")
    public ResponseEntity<List<Permission>> getAssignedPermissionOfRole(@PathVariable("roleId") Long roleId) {
        List<Permission> assignedPermissionOfRole = this.rolePermissionService.getAssignedPermissionOfRole(roleId);
        return ResponseEntity.ok().body(assignedPermissionOfRole);
    }


    @PostMapping("assign-permission")
    public ResponseEntity<RolePermission> assignPermissionToRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.assignPermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }

    @PostMapping("revoke-permission")
    public ResponseEntity<RolePermission> revokePermissionFromRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.revokePermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }
}
