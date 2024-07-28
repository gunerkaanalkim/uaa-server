package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.repository.RoleUserRepository;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.common.exception.RoleAlreadyAssignedException;
import org.kaanalkim.common.model.enums.ErrorCode;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleUserServiceImpl extends AbstractCrudService<RoleUser> implements RoleUserService {
    private final RoleUserRepository roleUserRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final RolePermissionService rolePermissionService;

    @Override
    @SuppressWarnings("unchecked")
    protected RoleUserRepository getRepository() {
        return this.roleUserRepository;
    }

    @Override
    public RoleUser assignRoleToUser(RoleToUser roleToUser) {
        Role role = this.roleService.get(roleToUser.getRoleId());
        User user = this.userService.get(roleToUser.getUserId());

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
        User user = this.userService.get(roleToUser.getUserId());

        Optional<RoleUser> oldRoleAndUser = getRepository().findByUser(user);

        if (oldRoleAndUser.isEmpty()) {
            return null;
        }

        this.roleUserRepository.delete(oldRoleAndUser.get());

        return oldRoleAndUser.get();
    }

    @Override
    public RoleUser findByUserId(int userId) {
        User user = this.userService.get((long) userId);

        Optional<RoleUser> roleUser = getRepository().findByUser(user);

        return roleUser.orElse(null);
    }

    @Override
    public AuthorizationVerificationResponse hasPermission(String username, String requestPath, long realmId) {
        User user = this.userService.findUserByUsernameAndRealmId(username, realmId);

        Optional<RoleUser> roleUser = this.roleUserRepository.findByUser(user);

        if (roleUser.isPresent()) {
            List<Permission> assignedPermissionsOfRole = this.rolePermissionService
                    .getAssignedPermissionsOfRole(roleUser.get().getRole().getId());

            Permission assignedPermission = assignedPermissionsOfRole
                    .parallelStream()
                    .filter(permission -> requestPath.contains(permission.getUrl()))
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
