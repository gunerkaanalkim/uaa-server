package org.kaanalkim.authserver.mapper;

import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.service.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class RealmMapper implements BaseMapper<Realm, RealmDTO> {
    @Override
    public RealmDTO toDTO(Realm e) {
        return RealmDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .code(e.getCode())
                .description(e.getDescription())
                .build();
    }

    @Override
    public Realm toEntity(RealmDTO realmDTO) {
        return Realm.builder()
                .id(realmDTO.getId())
                .name(realmDTO.getName())
                .description(realmDTO.getDescription())
                .code(realmDTO.getCode())
                .build();
    }
}
