package org.kaanalkim.authserver.controller;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.payload.request.*;
import org.kaanalkim.authserver.payload.response.AuthResponse;
import org.kaanalkim.authserver.payload.response.AuthorizationVerificationResponse;
import org.kaanalkim.authserver.payload.response.JWTVerificationResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.security.JwtTokenUtil;
import org.kaanalkim.authserver.service.AuthenticationService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "has-permission")
    public ResponseEntity<AuthorizationVerificationResponse> hasPermission(@RequestBody AuthorizationVerificationRequest authorizationVerificationRequest) {

        AuthorizationVerificationResponse authorizationVerificationResponse = this.roleUserService
                .hasPermission(authorizationVerificationRequest.getUsername(), authorizationVerificationRequest.getRequestPath());

        return ResponseEntity.ok().body(authorizationVerificationResponse);
    }
}
   