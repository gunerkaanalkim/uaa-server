package org.kaanalkim.authserver.mapper;

import org.kaanalkim.authserver.mapper.base.BaseMapper;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserMapper implements BaseMapper<User, UserDTO> {
    private final RealmMapper realmMapper;
    @Override
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .realm(realmMapper.toDTO(user.getRealm()))
                .build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .realm(this.realmMapper.toEntity(userDTO.getRealm()))
                .build();
    }
}

