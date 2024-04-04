package org.kaanalkim.authserver.mapper;

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
                .title(permission.getTitle())
                .controller(permission.getController())
                .description(permission.getDescription())
                .url(permission.getUrl())
                .groupName(permission.getGroupName())
                .build();
    }

    @Override
    public Permission toEntity(PermissionDTO permissionDTO) {
        return Permission.builder()
                .id(permissionDTO.getId())
                .title(permissionDTO.getTitle())
                .controller(permissionDTO.getController())
                .url(permissionDTO.getUrl())
                .description(permissionDTO.getDescription())
                .groupName(permissionDTO.getGroupName())
                .build();
    }
}
