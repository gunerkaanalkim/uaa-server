package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.mapper.RealmMapper;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.common.controller.base.AbstractController;
import org.kaanalkim.common.exception.RecordCannotBeDeletedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("realm")
@AllArgsConstructor
public class RealmController extends AbstractController<Realm, RealmDTO> {
    private final RealmService realmService;
    private final RealmMapper realmMapper;

    @Override
    @SuppressWarnings("unchecked")
    protected RealmService getService() {
        return this.realmService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RealmMapper getMapper() {
        return this.realmMapper;
    }

    @Override
    public ResponseEntity<RealmDTO> delete(Long id) {
        this.realmService.isMainRealm(id);

        return super.delete(id);
    }

    @Override
    public ResponseEntity<List<RealmDTO>> deleteAll(List<RealmDTO> all) {
        all.forEach(realmDTO -> this.realmService.isMainRealm(realmDTO.getId()));

        return super.deleteAll(all);
    }
}
