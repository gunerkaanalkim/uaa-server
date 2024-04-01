package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.exception.NotFoundException;
import org.kaanalkim.authserver.exception.RoleAlreadyAssignedException;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.enums.ErrorCode;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.repository.RoleUserRepository;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleUserServiceImpl extends AbstractCrudService<RoleUser, RoleUserDTO> implements RoleUserService {
    private final RoleUserRepository roleUserRepository;
    private final RoleUserMapper roleUserMapper;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public RoleUserServiceImpl(RoleUserRepository roleUserRepository, RoleUserMapper roleUserMapper, RoleMapper roleMapper, UserMapper userMapper1, RoleService roleService, UserService userService) {
        this.roleUserRepository = roleUserRepository;
        this.roleUserMapper = roleUserMapper;
        this.roleMapper = roleMapper;
        this.userMapper = userMapper1;
        this.roleService = roleService;
        this.userService = userService;
    }


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
    public Optional<RoleUser> findByRoleAndUser(Role role, User user) {
        return this.roleUserRepository.findByRoleAndUser(role, user);
    }

    @Override
    public RoleUser assignRoleToUser(RoleToUser roleToUser) {
        RoleDTO roleDTO = this.roleService.get(roleToUser.getRoleId());
        UserDTO userDTO = this.userService.get(roleToUser.getUserId());

        Role role = this.roleMapper.toEntity(roleDTO);
        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> oldRoleAndUser = this.findByRoleAndUser(role, user);

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
        RoleDTO roleDTO = this.roleService.get(roleToUser.getRoleId());
        UserDTO userDTO = this.userService.get(roleToUser.getUserId());

        Role role = this.roleMapper.toEntity(roleDTO);
        User user = this.userMapper.toEntity(userDTO);

        Optional<RoleUser> oldRoleAndUser = this.findByRoleAndUser(role, user);

        if(!oldRoleAndUser.isPresent()) {
            throw new NotFoundException(super.getErrorMessage(ErrorCode.ROLE_USER_NOT_FOUND, ""));
        }

        this.roleUserRepository.delete(oldRoleAndUser.get());

        return oldRoleAndUser.get();
    }
}
