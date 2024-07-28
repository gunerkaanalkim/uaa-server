package org.kaanalkim.authserver.controller;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.exception.UserAlreadyExistException;
import org.kaanalkim.authserver.mapper.UserMapper;
import org.kaanalkim.authserver.mapper.impl.RoleUserMapperImpl;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.common.controller.base.AbstractController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController extends AbstractController<User, UserDTO> {
    private final UserService userService;
    private final RoleUserService roleUserService;
    private final UserMapper userMapper;
    private final RoleUserMapperImpl roleUserMapperImpl;

    @Override
    @SuppressWarnings("unchecked")
    protected UserService getService() {
        return this.userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserMapper getMapper() {
        return this.userMapper;
    }

    @PostMapping(value = "change-password")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePassword entity) {
        UserDTO userDTO = this.userMapper.toDTO(this.userService.changePassword(entity));

        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("assign-role")
    public ResponseEntity<RoleUserDTO> assignRoleToUser(@RequestBody RoleToUser roleToUser) {
        RoleUserDTO roleUserDTO = this.roleUserMapperImpl.toDTO(this.roleUserService.assignRoleToUser(roleToUser));
        return ResponseEntity.ok().body(roleUserDTO);
    }

    @PostMapping("revoke-role")
    public ResponseEntity<RoleUserDTO> revokeRoleFromUser(@RequestBody RoleToUser roleToUser) {
        RoleUserDTO roleUserDTO = this.roleUserMapperImpl.toDTO(this.roleUserService.revokeRoleToUser(roleToUser));
        return ResponseEntity.ok().body(roleUserDTO);
    }

    @GetMapping("get-assigned-role/{userId}")
    public ResponseEntity<RoleUserDTO> getAssignedRole(@PathVariable("userId") int userId) {
        RoleUserDTO roleUserDTO = this.roleUserMapperImpl.toDTO(this.roleUserService.findByUserId(userId));
        return ResponseEntity.ok().body(roleUserDTO);
    }

    @Override
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        this.userService.isUserExist(userDTO);
        return super.create(userDTO);
    }
}
