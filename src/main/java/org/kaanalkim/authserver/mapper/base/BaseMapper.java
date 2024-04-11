package org.kaanalkim.authserver.mapper.base;

import org.kaanalkim.authserver.mapper.AbstractDTO;
import org.kaanalkim.authserver.model.base.AbstractEntity;

public interface BaseMapper<T extends AbstractEntity, D extends AbstractDTO> {
    D toDTO(T e);

    T toEntity(D d);
}
