package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.mapper.PermissionMapper;
import org.kaanalkim.authserver.mapper.RoleMapper;
import org.kaanalkim.authserver.mapper.RolePermissionMapper;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.dto.RolePermissionDTO;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
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
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;


    @Override
    @SuppressWarnings("unchecked")
    protected RoleService getService() {
        return this.roleService;
    }


    @Override
    @SuppressWarnings("unchecked")
    protected RoleMapper getMapper() {
        return this.roleMapper;
    }

    @GetMapping("/get-assigned-permissions/{roleId}")
    public ResponseEntity<List<PermissionDTO>> getAssignedPermissionOfRole(@PathVariable("roleId") Long roleId) {
        List<PermissionDTO> permissionDTOS = this.rolePermissionService.getAssignedPermissionsOfRole(roleId)
                .stream()
                .map(this.permissionMapper::toDTO)
                .toList();

        return ResponseEntity.ok().body(permissionDTOS);
    }


    @PostMapping("assign-permission")
    public ResponseEntity<RolePermissionDTO> assignPermissionToRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermissionDTO dto = this.rolePermissionMapper.toDTO(
                this.rolePermissionService.assignPermissionToRole(permissionToRole)
        );

        return ResponseEntity.ok().body(dto);
    }


    @PostMapping("assign-all-permissions")
    public ResponseEntity<List<RolePermissionDTO>> assignAllPermissionToRole(@RequestBody PermissionsToRole permissionsToRole) {
        List<RolePermissionDTO> rolePermissionDTOS = this.rolePermissionService.assignAllPermissionToRole(permissionsToRole)
                .stream()
                .map(this.rolePermissionMapper::toDTO)
                .toList();

        return ResponseEntity.ok().body(rolePermissionDTOS);
    }


    @PostMapping("revoke-permission")
    public ResponseEntity<RolePermissionDTO> revokePermissionFromRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermissionDTO rolePermissionDTO = this.rolePermissionMapper.toDTO(
                this.rolePermissionService.revokePermissionToRole(permissionToRole)
        );

        return ResponseEntity.ok().body(rolePermissionDTO);
    }

    @PostMapping("revoke-all-permissions")
    public ResponseEntity<RoleDTO> revokeAllPermissionFromRole(@RequestBody PermissionsToRole permissionsToRole) {
        RoleDTO roleDTO = this.roleMapper.toDTO(this.rolePermissionService.revokeAllPermissionFromRole(permissionsToRole));
        return ResponseEntity.ok().body(roleDTO);
    }
}
