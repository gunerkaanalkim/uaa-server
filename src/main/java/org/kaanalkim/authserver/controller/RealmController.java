package org.kaanalkim.authserver.controller;


import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.controller.base.AbstractController;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.payload.dto.RealmDTO;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.RealmService;
import org.kaanalkim.authserver.service.base.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
