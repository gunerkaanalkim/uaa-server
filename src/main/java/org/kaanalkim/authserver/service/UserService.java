package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.common.service.base.BaseCrudService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends BaseCrudService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);

    User changePassword(ChangePassword changePassword);

    UserInfo getByUsername(String username);

    boolean isUserExist(UserDTO userDTO);

    User findUserByUsernameAndRealmId(String username, long realmId);
}
