package org.kaanalkim.authserver.controller;


import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
public class RoleController extends AbstractController<Role, RoleDTO> {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected RoleService getService() {
        return this.roleService;
    }
}
