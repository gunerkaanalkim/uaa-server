package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.repository.RealmRepository;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RealmServiceImpl extends AbstractCrudService<Realm> implements RealmService {
    private final RealmRepository realmRepository;

    @Override
    @SuppressWarnings("unchecked")
    protected RealmRepository getRepository() {
        return realmRepository;
    }
}
