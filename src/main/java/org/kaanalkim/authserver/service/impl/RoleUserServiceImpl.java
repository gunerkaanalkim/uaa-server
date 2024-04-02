package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.exception.RoleAlreadyAssignedException;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.enums.ErrorCode;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.repository.RoleUserRepository;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleUserServiceImpl extends AbstractCrudService<RoleUser, RoleUserDTO> implements RoleUserService {
    private final RoleUserRepository roleUserRepository;
    private final RoleUserMapper roleUserMapper;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserService userService;

    private final RolePermissionService rolePermissionService;

    @Override
    @SuppressWarnings("unchecked")
    protected RoleUserRepository getRepository() {
        return this.roleUserRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RoleUserMapper getMapper() {
        return this.roleUserMapper;
    }

    @Override
    public RoleUser assignRoleToUser(RoleToUser roleToUser) {
        RoleDTO roleDTO = this.roleService.get(roleToUser.getRoleId());
        UserDTO userDTO = this.userService.get(roleToUser.getUserId());

        Role role = this.roleMapper.toEntity(roleDTO);
        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> oldRoleAndUser = getRepository().findByUser(user);

        oldRoleAndUser.ifPresent(roleUser -> {
            throw new RoleAlreadyAssignedException(super.getErrorMessage(ErrorCode.ROLE_ALREADY_ASSIGNED, roleUser.getId().toString()));
        });

        RoleUser roleUser = RoleUser.builder()
                .role(role)
                .user(user)
                .build();

        this.roleUserRepository.save(roleUser);

        return roleUser;
    }

    @Override
    public RoleUser revokeRoleToUser(RoleToUser roleToUser) {
        UserDTO userDTO = this.userService.get(roleToUser.getUserId());

        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> oldRoleAndUser = getRepository().findByUser(user);

        if (oldRoleAndUser.isEmpty()) {
            return null;
        }

        this.roleUserRepository.delete(oldRoleAndUser.get());

        return oldRoleAndUser.get();
    }

    @Override
    public RoleUser findByUserId(int userId) {
        UserDTO userDTO = this.userService.get((long) userId);

        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> roleUser = getRepository().findByUser(user);

        return roleUser.orElse(null);
    }

    @Override
    public AuthorizationVerificationResponse hasPermission(int userId, String requestPath) {
        UserDTO userDTO = this.userService.get((long) userId);
        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> roleUser = this.roleUserRepository.findByUser(user);

        if (roleUser.isPresent()) {
            List<Permission> assignedPermissionsOfRole = this.rolePermissionService
                    .getAssignedPermissionsOfRole(roleUser.get().getRole().getId());

            Permission assignedPermission = assignedPermissionsOfRole
                    .parallelStream()
                    .filter(permission -> {
                        String requestPathOfPermission = String.format("/%s/%s", permission.getController(), permission.getUrl());

                        return requestPathOfPermission.equalsIgnoreCase(requestPath);
                    })
                    .findFirst()
                    .orElse(null);

            if (Objects.nonNull(assignedPermission)) {
                return AuthorizationVerificationResponse.builder()
                        .hasPermission(true)
                        .build();
            }
        }

        return AuthorizationVerificationResponse.builder()
                .hasPermission(false)
                .build();
    }
}
