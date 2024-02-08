package org.kaanalkim.authserver.controller;

import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController extends AbstractController<User, UserDTO> {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserService getService() {
        return this.userService;
    }

    @PostMapping(value="change-password")
    public ResponseEntity<User> changePassword(@RequestBody ChangePassword entity) {
        User user = this.userService.changePassword(entity);

        return ResponseEntity.ok().body(user);
    }
}
