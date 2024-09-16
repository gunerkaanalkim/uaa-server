package org.kaanalkim.authserver.controller;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.mapper.UserMapper;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.request.AuthorizationVerificationRequest;
import org.kaanalkim.authserver.payload.request.Credential;
import org.kaanalkim.authserver.payload.request.EmailDetails;
import org.kaanalkim.authserver.payload.response.AuthResponse;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.payload.response.JWTVerificationResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.security.JwtService;
import org.kaanalkim.authserver.service.AuthenticationService;
import org.kaanalkim.authserver.service.EmailService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.common.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("authenticate")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleUserService roleUserService;
    private final EmailService emailService;

    @PostMapping("/login/realm/{realmId}")
    public ResponseEntity<AuthResponse> login(@PathVariable("realmId") long realmId, @RequestBody Credential credential) throws Exception {

        User user = userService.findUserByUsernameAndRealmId(credential.getUsername(), realmId);

        final String token = jwtService.generateToken(user);

        this.authenticationService.authenticate(credential.getUsername(), credential.getPassword());

        UserInfo userInfo = userService.getByUsername(credential.getUsername());

        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .userInfo(userInfo)
                .build();

        return ResponseEntity.ok(authResponse);
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
        JWTVerificationResponse jwtVerificationResponse = this.jwtService
                .verifyToken(bearerToken.substring(7));

        return ResponseEntity.ok().body(jwtVerificationResponse);
    }

    @PostMapping(value = "has-permission")
    public ResponseEntity<AuthorizationVerificationResponse> hasPermission(
            @RequestBody AuthorizationVerificationRequest authorizationVerificationRequest
    ) throws UserNotFoundException {
        AuthorizationVerificationResponse authorizationVerificationResponse = this.roleUserService.hasPermission(
                authorizationVerificationRequest.getUsername(),
                authorizationVerificationRequest.getRequestPath(),
                authorizationVerificationRequest.getRealmId()
        );

        return ResponseEntity.ok().body(authorizationVerificationResponse);
    }
}
   