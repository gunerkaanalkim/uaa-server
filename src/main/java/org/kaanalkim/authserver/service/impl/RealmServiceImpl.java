package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.repository.RealmRepository;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.common.constant.CommonConstants;
import org.kaanalkim.common.exception.RecordCannotBeDeletedException;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RealmServiceImpl extends AbstractCrudService<Realm> implements RealmService {
    private final RealmRepository realmRepository;

    @Override
    @SuppressWarnings("unchecked")
    protected RealmRepository getRepository() {
        return realmRepository;
    }

    @Override
    public void isMainRealm(long realmId) {
        Optional<Realm> mainRealm = this.realmRepository.findById(realmId);

        if (mainRealm.isPresent() && mainRealm.get().getCode().equals(CommonConstants.MAIN_REALM_CODE)) {
            throw new RecordCannotBeDeletedException("This record cannot be deleted!");
        }
    }
}
