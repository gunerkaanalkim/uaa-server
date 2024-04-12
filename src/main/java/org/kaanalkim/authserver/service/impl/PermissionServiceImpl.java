package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.repository.PermissionRepository;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionServiceImpl extends AbstractCrudService<Permission> implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    @SuppressWarnings("unchecked")
    protected PermissionRepository getRepository() {
        return this.permissionRepository;
    }
}
