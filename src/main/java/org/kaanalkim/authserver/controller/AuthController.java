package org.kaanalkim.authserver.controller;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.*;
import org.kaanalkim.authserver.payload.response.AuthResponse;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.payload.response.JWTVerificationResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.security.JwtTokenUtil;
import org.kaanalkim.authserver.service.AuthenticationService;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.authserver.service.impl.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authenticate")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleUserService roleUserService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Credential credential) throws Exception {

        UserDetails userDetails = userService.loadUserByUsername(credential.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        this.authenticationService.authenticate(credential.getUsername(), credential.getPassword());

        /* List<Privileges> privileges = roleEmployeeService.getPrivilegesByUsername(credential.getUsername());*/
        UserInfo userSecret = userService.getByUsername(credential.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, userSecret));
    }

    @GetMapping(value = "who-am-i")
    public ResponseEntity<UserInfo> whoAmI() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfo userInfo = this.userService.getByUsername(userName);

        return ResponseEntity.ok().body(userInfo);
    }

    @GetMapping(value = "verify-token")
    public ResponseEntity<JWTVerificationResponse> verifyToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        JWTVerificationResponse jwtVerificationResponse = this.jwtTokenUtil
                .verifyToken(bearerToken.substring(7));

        return ResponseEntity.ok().body(jwtVerificationResponse);
    }

    @GetMapping(value = "has-permission/{userId}/{requestPath}")
    public ResponseEntity<AuthorizationVerificationResponse> hasPermission(
            @PathVariable("userId") int userId,
            @PathVariable("requestPath") String requestPath) {

        AuthorizationVerificationResponse authorizationVerificationResponse = this.roleUserService
                .hasPermission(userId, requestPath);

        return ResponseEntity.ok().body(authorizationVerificationResponse);
    }
}
   