package org.kaanalkim.authserver.controller;

import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController extends AbstractController<User, UserDTO> {
    private final UserService userService;
    private final RoleUserService roleUserService;

    @PostMapping(value="change-password")
    public ResponseEntity<User> changePassword(@RequestBody ChangePassword entity) {
        User user = this.userService.changePassword(entity);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("assign-role")
    public ResponseEntity<RoleUser> assignRoleToUser(@RequestBody RoleToUser roleToUser) {
        RoleUser roleUser = this.roleUserService.assignRoleToUser(roleToUser);
        return ResponseEntity.ok().body(roleUser);
    }

    @PostMapping("revoke-role")
    public ResponseEntity<RoleUser> revokeRoleFromUser(@RequestBody RoleToUser roleToUser) {
        RoleUser roleUser = this.roleUserService.revokeRoleToUser(roleToUser);
        return ResponseEntity.ok().body(roleUser);
    }

    @GetMapping("get-assigned-role/{userId}")
    public ResponseEntity<RoleUser> getAssignedRole(@PathVariable("userId") int userId) {
        RoleUser byUserId = this.roleUserService.findByUserId(userId);
        return ResponseEntity.ok().body(byUserId);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserService getService() {
        return this.userService;
    }
}
