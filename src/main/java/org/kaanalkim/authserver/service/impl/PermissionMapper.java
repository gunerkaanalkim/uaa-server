package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.service.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class PermissionMapper implements BaseMapper<Permission, PermissionDTO> {
    @Override
    public PermissionDTO toDTO(Permission permission) {
        return PermissionDTO.builder()
                .id(permission.getId())
                .permissionName(permission.getPermissionName())
                .controller(permission.getController())
                .controllerAlias(permission.getControllerAlias())
                .description(permission.getDescription())
                .build();
    }

    @Override
    public Permission toEntity(PermissionDTO permissionDTO) {
        return Permission.builder()
                .id(permissionDTO.getId())
                .permissionName(permissionDTO.getPermissionName())
                .controller(permissionDTO.getController())
                .controllerAlias(permissionDTO.getControllerAlias())
                .description(permissionDTO.getDescription())
                .build();
    }
}
