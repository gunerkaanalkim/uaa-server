package org.kaanalkim.authserver.controller;


import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.service.RealmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("realm")
@AllArgsConstructor
public class RealmController extends AbstractController<Realm, RealmDTO> {
    private final RealmService realmService;

    @Override
    @SuppressWarnings("unchecked")
    protected RealmService getService() {
        return this.realmService;
    }
}
