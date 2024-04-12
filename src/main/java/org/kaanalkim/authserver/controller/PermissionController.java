package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.mapper.impl.PermissionMapper;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permission")
@AllArgsConstructor
public class PermissionController extends AbstractController<Permission, PermissionDTO> {
    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;

    @Override
    @SuppressWarnings("unchecked")
    protected PermissionService getService() {
        return this.permissionService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected PermissionMapper getMapper() {
        return this.permissionMapper;
    }
}
