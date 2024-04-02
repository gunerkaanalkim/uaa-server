package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.service.base.BaseCrudService;

import java.util.Optional;

public interface RoleUserService extends BaseCrudService<RoleUser, RoleUserDTO> {
    RoleUser assignRoleToUser(RoleToUser roleToUser);
    RoleUser revokeRoleToUser(RoleToUser roleToUser);

    RoleUser findByUserId(int userId);

    AuthorizationVerificationResponse hasPermission(int userId, String requestPath);
}
