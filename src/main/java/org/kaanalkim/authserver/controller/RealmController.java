package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.mapper.RealmMapper;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.common.controller.base.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
