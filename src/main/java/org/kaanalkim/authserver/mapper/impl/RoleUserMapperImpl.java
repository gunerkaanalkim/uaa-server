package org.kaanalkim.authserver.mapper.impl;

import org.kaanalkim.authserver.mapper.RoleUserMapper;
import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.payload.dto.RoleUserDTO;
import org.springframework.stereotype.Service;

@Service
public class RoleUserMapperImpl implements RoleUserMapper {
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
