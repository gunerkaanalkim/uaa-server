package org.kaanalkim.authserver.service.mapper;

import org.kaanalkim.authserver.model.base.AbstractEntity;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

public interface BaseMapper<T extends AbstractEntity, D extends AbstractDTO> {
    D toDTO(T e);

    T toEntity(D d);
}
