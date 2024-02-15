package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.service.base.BaseCrudService;

import java.util.Optional;

public interface RoleUserService extends BaseCrudService<RoleUser, RoleUserDTO> {
    RoleUser assignRoleToUser(RoleToUser roleToUser);
    RoleUser unassignRoleToUser(RoleToUser roleToUser);
    Optional<RoleUser> findByRoleAndUser(Role role, User user);
}
