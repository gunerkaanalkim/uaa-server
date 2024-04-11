package org.kaanalkim.authserver.mapper;


import org.kaanalkim.authserver.mapper.base.BaseMapper;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper implements BaseMapper<Role, RoleDTO> {
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
