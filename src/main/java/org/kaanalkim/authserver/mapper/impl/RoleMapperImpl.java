package org.kaanalkim.authserver.mapper.impl;


import org.kaanalkim.authserver.mapper.RoleMapper;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.springframework.stereotype.Service;

@Service
public class RoleMapperImpl implements RoleMapper {
    @Override
    public RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .build();
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .code(roleDTO.getCode())
                .build();
    }
}
