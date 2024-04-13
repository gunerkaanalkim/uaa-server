package org.kaanalkim.authserver.mapper.impl;

import org.kaanalkim.authserver.mapper.RolePermissionMapper;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.dto.RolePermissionDTO;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionMapperImpl implements RolePermissionMapper {
    @Override
    public RolePermissionDTO toDTO(RolePermission rolePermission) {
        return RolePermissionDTO.builder()
                .id(rolePermission.getId())
                .role(rolePermission.getRole())
                .permission(rolePermission.getPermission())
                .build();
    }

    @Override
    public RolePermission toEntity(RolePermissionDTO rolePermissionDTO) {
        return RolePermission.builder()
                .id(rolePermissionDTO.getId())
                .role(rolePermissionDTO.getRole())
                .permission(rolePermissionDTO.getPermission())
                .build();
    }
}
