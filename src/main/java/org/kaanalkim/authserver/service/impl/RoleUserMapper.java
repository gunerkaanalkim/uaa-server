package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.kaanalkim.authserver.service.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleUserMapper implements BaseMapper<RoleUser, RoleUserDTO> {
    @Override
    public RoleUserDTO toDTO(RoleUser roleUser) {
        return RoleUserDTO.builder()
                .id(roleUser.getId())
                .user(roleUser.getUser())
                .role(roleUser.getRole())
                .build();
    }

    @Override
    public RoleUser toEntity(RoleUserDTO roleUserDTO) {
        return RoleUser.builder()
                .id(roleUserDTO.getRole().getId())
                .user(roleUserDTO.getUser())
                .role(roleUserDTO.getRole())
                .build();
    }
}
