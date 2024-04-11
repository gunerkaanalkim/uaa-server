package org.kaanalkim.authserver.mapper;

import org.kaanalkim.authserver.mapper.base.BaseMapper;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.payload.dto.RolePermissionDTO;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionMapper implements BaseMapper<RolePermission, RolePermissionDTO> {
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
