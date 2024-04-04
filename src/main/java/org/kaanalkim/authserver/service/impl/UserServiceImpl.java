package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.exception.NotFoundException;
import org.kaanalkim.authserver.exception.UserAlreadyRegisteredException;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.mapper.UserMapper;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.repository.UserRepository;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractCrudService<User, UserDTO> implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserMapper getMapper() {
        return this.userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void existsByUsername(String username) {
        final Boolean isExist = this.userRepository.existsByUsername(username);

        if (isExist) {
            throw new UserAlreadyRegisteredException("Error: Username is already in use!");
        }
    }

    @Override
    public void existsByEmail(String email) {
        final Boolean isExist = this.userRepository.existsByEmail(email);

        if (isExist) {
            throw new UserAlreadyRegisteredException("Error: Email is already in use!");
        }
    }

    @Override
    public User changePassword(ChangePassword changePassword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> oldUser = this.userRepository.findByUsername(userName);

        if (!oldUser.isPresent()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        User user = oldUser.get();

        if(changePassword.getPassword().equals(changePassword.getRePassword())) {
            user.setPassword(changePassword.getPassword());
        }

        this.userRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    @Override
    public UserInfo getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }
}
