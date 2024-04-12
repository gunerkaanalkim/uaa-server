package org.kaanalkim.authserver.mapper.impl;

import org.kaanalkim.authserver.mapper.RealmMapper;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.springframework.stereotype.Service;

@Service
public class RealmMapperImpl implements RealmMapper {
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
