package org.kaanalkim.authserver.controller;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.payload.request.Credential;
import org.kaanalkim.authserver.payload.request.PermissionToRole;
import org.kaanalkim.authserver.payload.request.PermissionsToRole;
import org.kaanalkim.authserver.payload.request.RoleToUser;
import org.kaanalkim.authserver.payload.response.AuthResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.security.JwtTokenUtil;
import org.kaanalkim.authserver.service.AuthenticationService;
import org.kaanalkim.authserver.service.RolePermissionService;
import org.kaanalkim.authserver.service.RoleUserService;
import org.kaanalkim.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authenticate")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleUserService roleUserService;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public AuthController(AuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, UserService userService, RoleUserService roleUserService, RolePermissionService rolePermissionService) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.roleUserService = roleUserService;
        this.rolePermissionService = rolePermissionService;
    }

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

    @PostMapping("assign-role")
    public ResponseEntity<RoleUser> assignRoleToUser(@RequestBody RoleToUser roleToUser) {
        RoleUser roleUser = this.roleUserService.assignRoleToUser(roleToUser);
        return ResponseEntity.ok().body(roleUser);
    }

    @PostMapping("unassign-role")
    public ResponseEntity<RoleUser> unassignRoleFromUser(@RequestBody RoleToUser roleToUser) {
        RoleUser roleUser = this.roleUserService.unassignRoleToUser(roleToUser);
        return ResponseEntity.ok().body(roleUser);
    }

    @PostMapping("assign-permission")
    public ResponseEntity<RolePermission> assignPermissionToRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.assignPermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }

    @PostMapping("unassign-permission")
    public ResponseEntity<RolePermission> unassignPermissionFromRole(@RequestBody PermissionToRole permissionToRole) {
        RolePermission rolePermission = this.rolePermissionService.unassignPermissionToRole(permissionToRole);
        return ResponseEntity.ok().body(rolePermission);
    }

    @PostMapping("unassign-all-permission")
    public ResponseEntity<Role> unassignAllPermissionFromRole(@RequestBody PermissionsToRole permissionsToRole) {
        Role role = this.rolePermissionService.unassignAllPermissionFromRole(permissionsToRole);
        return ResponseEntity.ok().body(role);
    }

    @PostMapping("assign-all-permission")
    public ResponseEntity<List<RolePermission>> assignAllPermissionToRole(@RequestBody PermissionsToRole permissionsToRole) {
        List<RolePermission> rolePermissions = this.rolePermissionService.assignAllPermissionToRole(permissionsToRole);
        return ResponseEntity.ok().body(rolePermissions);
    }

    @GetMapping("role/{roleId}/get-assigned-permissions")
    public ResponseEntity<List<Permission>> getAssignedPermissionOfRole(@PathVariable("roleId") Long roleId) {
        List<Permission> assignedPermissionOfRole = this.rolePermissionService.getAssignedPermissionOfRole(roleId);
        return ResponseEntity.ok().body(assignedPermissionOfRole);
    }
}
   