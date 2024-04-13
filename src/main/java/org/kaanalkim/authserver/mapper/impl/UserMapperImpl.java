package org.kaanalkim.authserver.mapper.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.mapper.UserMapper;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final RealmMapperImpl realmMapperImpl;
    @Override
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .username(user.getUsername())
                .realm(realmMapperImpl.toDTO(user.getRealm()))
                .build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .realm(this.realmMapperImpl.toEntity(userDTO.getRealm()))
                .build();
    }
}

