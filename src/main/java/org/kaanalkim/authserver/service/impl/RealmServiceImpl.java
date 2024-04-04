package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.mapper.RealmMapper;
import org.kaanalkim.authserver.repository.RealmRepository;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RealmServiceImpl extends AbstractCrudService<Realm, RealmDTO> implements RealmService {
    private final RealmRepository realmRepository;
    private final RealmMapper realmMapper;

    @Override
    @SuppressWarnings("unchecked")
    protected RealmRepository getRepository() {
        return realmRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RealmMapper getMapper() {
        return realmMapper;
    }
}
