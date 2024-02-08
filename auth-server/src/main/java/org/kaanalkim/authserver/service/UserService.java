package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.service.base.BaseCrudService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends BaseCrudService<User, UserDTO>, UserDetailsService {
    Optional<User> findByUsername(String username);

    void existsByUsername(String username);

    void existsByEmail(String email);

    User changePassword(ChangePassword changePassword);

    UserInfo getByUsername(String username);
}
