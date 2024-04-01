package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.exception.NotFoundException;
import org.kaanalkim.authserver.exception.PermissionAlreadyAssignedException;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.model.enums.ErrorCode;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.dto.RolePermissionDTO;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
import org.kaanalkim.authserver.repository.RolePermissionRepository;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl extends AbstractCrudService<RolePermission, RolePermissionDTO> implements RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RolePermissionMapper rolePermissionMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository, RoleService roleService, PermissionService permissionService, RolePermissionMapper rolePermissionMapper, RoleMapper roleMapper, PermissionMapper permissionMapper) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.rolePermissionMapper = rolePermissionMapper;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RolePermissionRepository getRepository() {
        return this.rolePermissionRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RolePermissionMapper getMapper() {
        return this.rolePermissionMapper;
    }

    @Override
    public Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission) {
        return this.rolePermissionRepository.findByRoleAndPermission(role, permission);
    }

    @Override
    public RolePermission assignPermissionToRole(PermissionToRole permissionToRole) {
        RoleDTO roleDTO = this.roleService.get(permissionToRole.getRoleId());
        PermissionDTO permissionDTO = this.permissionService.get(permissionToRole.getPermissionId());

        Role role = this.roleMapper.toEntity(roleDTO);
        Permission permission = this.permissionMapper.toEntity(permissionDTO);

        Optional<RolePermission> oldRoleAndPermission = this.findByRoleAndPermission(role, permission);

        oldRoleAndPermission.ifPresent(rolePermission -> {
            throw new PermissionAlreadyAssignedException(super.getErrorMessage(ErrorCode.PERMISSION_ALREADY_ASSIGNED, rolePermission.getId().toString()));
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
        RoleDTO roleDTO = this.roleService.get(permissionToRole.getRoleId());
        PermissionDTO permissionDTO = this.permissionService.get(permissionToRole.getPermissionId());

        Role role = this.roleMapper.toEntity(roleDTO);
        Permission permission = this.permissionMapper.toEntity(permissionDTO);

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
        RoleDTO roleDTO = this.roleService.get(permissionsToRole.getRoleId());
        Role role = this.roleMapper.toEntity(roleDTO);

        this.rolePermissionRepository.deleteAllByRole(role);

        return role;
    }

    @Override
    public List<RolePermission> assignAllPermissionToRole(PermissionsToRole permissionsToRole) {
        RoleDTO roleDTO = this.roleService.get(permissionsToRole.getRoleId());
        Role role = this.roleMapper.toEntity(roleDTO);

        List<RolePermission> rolePermissions = new LinkedList<>();

        permissionsToRole.getPermissionIds().forEach(permissionId -> {
            PermissionDTO permissionDTO = this.permissionService.get(permissionId);
            Permission permission = this.permissionMapper.toEntity(permissionDTO);

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
    public List<Permission> getAssignedPermissionOfRole(Long roleId) {
        RoleDTO roleDTO = this.roleService.get(roleId);
        Role role = this.roleMapper.toEntity(roleDTO);

        List<RolePermission> allByRole = this.rolePermissionRepository.findAllByRole(role);

        return allByRole.stream().map(RolePermission::getPermission).collect(Collectors.toList());
    }
}
