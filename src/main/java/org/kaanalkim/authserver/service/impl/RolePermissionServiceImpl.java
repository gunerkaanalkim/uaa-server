package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
import org.kaanalkim.authserver.repository.RolePermissionRepository;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.common.exception.NotFoundException;
import org.kaanalkim.common.exception.PermissionAlreadyAssignedException;
import org.kaanalkim.common.model.enums.ErrorCode;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RolePermissionServiceImpl extends AbstractCrudService<RolePermission> implements RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Override
    @SuppressWarnings("unchecked")
    protected RolePermissionRepository getRepository() {
        return this.rolePermissionRepository;
    }

    @Override
    public Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission) {
        return this.rolePermissionRepository.findByRoleAndPermission(role, permission);
    }

    @Override
    public RolePermission assignPermissionToRole(PermissionToRole permissionToRole) {
        Role role = this.roleService.get(permissionToRole.getRoleId());
        Permission permission = this.permissionService.get(permissionToRole.getPermissionId());

        Optional<RolePermission> oldRoleAndPermission = this.findByRoleAndPermission(role, permission);

        oldRoleAndPermission.ifPresent(rolePermission -> {
            throw new PermissionAlreadyAssignedException(super.getErrorMessage(ErrorCode.PERMISSION_ALREADY_ASSIGNED,
                    rolePermission.getId().toString()));
        });

        RolePermission rolePermission = RolePermission.builder()
                .permission(permission)
                .role(role)
                .build();

        this.rolePermissionRepository.save(rolePermission);

        return rolePermission;
    }

    @Override
    public RolePermission revokePermissionToRole(PermissionToRole permissionToRole) {
        Role role = this.roleService.get(permissionToRole.getRoleId());
        Permission permission = this.permissionService.get(permissionToRole.getPermissionId());

        Optional<RolePermission> oldRoleAndPermission = this.findByRoleAndPermission(role, permission);

        if (oldRoleAndPermission.isEmpty()) {
            throw new NotFoundException(super.getErrorMessage(ErrorCode.ROLE_USER_NOT_FOUND, ""));
        }

        this.rolePermissionRepository.delete(oldRoleAndPermission.get());

        return oldRoleAndPermission.get();
    }

    @Override
    @Transactional
    public Role revokeAllPermissionFromRole(PermissionsToRole permissionsToRole) {
        Role role = this.roleService.get(permissionsToRole.getRoleId());

        this.rolePermissionRepository.deleteAllByRole(role);

        return role;
    }

    @Override
    public List<RolePermission> assignAllPermissionToRole(PermissionsToRole permissionsToRole) {
        Role role = this.roleService.get(permissionsToRole.getRoleId());

        List<RolePermission> rolePermissions = new LinkedList<>();

        permissionsToRole.getPermissionIds().forEach(permissionId -> {
            Permission permission = this.permissionService.get(permissionId);

            Optional<RolePermission> oldRoleAndPermission = this.rolePermissionRepository.findByRoleAndPermission(role, permission);

            if (oldRoleAndPermission.isEmpty()) {
                RolePermission rolePermission = RolePermission.builder()
                        .permission(permission)
                        .role(role)
                        .build();

                this.rolePermissionRepository.save(rolePermission);

                rolePermissions.add(rolePermission);
            }
        });

        return rolePermissions;
    }

    @Override
    public List<Permission> getAssignedPermissionsOfRole(Long roleId) {
        Role role = this.roleService.get(roleId);

        List<RolePermission> allByRole = this.rolePermissionRepository.findAllByRole(role);

        return allByRole.stream().map(RolePermission::getPermission).collect(Collectors.toList());
    }

}
