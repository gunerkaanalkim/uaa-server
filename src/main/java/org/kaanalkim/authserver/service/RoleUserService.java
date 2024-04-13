package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.common.service.base.BaseCrudService;

public interface RoleUserService extends BaseCrudService<RoleUser> {
    RoleUser assignRoleToUser(RoleToUser roleToUser);

    RoleUser revokeRoleToUser(RoleToUser roleToUser);

    RoleUser findByUserId(int userId);

    AuthorizationVerificationResponse hasPermission(String username, String requestPath, long realmId);
}
