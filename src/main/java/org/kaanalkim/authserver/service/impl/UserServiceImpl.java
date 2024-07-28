package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.exception.UserAlreadyExistException;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.repository.UserRepository;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.common.exception.NotFoundException;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {
    private final UserRepository userRepository;

    @Override
    @SuppressWarnings("unchecked")
    protected UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User changePassword(ChangePassword changePassword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> oldUser = this.userRepository.findByUsername(userName);

        if (oldUser.isEmpty()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        User user = oldUser.get();

        if (changePassword.getPassword().equals(changePassword.getRePassword())) {
            user.setPassword(changePassword.getPassword());
        }

        this.userRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    @Override
    public UserInfo getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public void isUserExist(UserDTO userDTO) {
        Optional<User> userByRealm = this.userRepository
                .findUserByUsernameAndRealmId(userDTO.getUsername(), userDTO.getRealm().getId());

        if (userByRealm.isPresent()) {
            throw new UserAlreadyExistException("User cannot be registered with given username.");
        }
    }

    @Override
    public User findUserByUsernameAndRealmId(String username, long realmId) {
        Optional<User> userByRealm = this.userRepository
                .findUserByUsernameAndRealmId(username, realmId);

        if (userByRealm.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        return userByRealm.get();
    }
}
