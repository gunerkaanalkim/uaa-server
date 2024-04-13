package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.repository.RoleRepository;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends AbstractCrudService<Role> implements RoleService {
    private RoleRepository roleRepository;

    @Override
    @SuppressWarnings("unchecked")
    protected RoleRepository getRepository() {
        return this.roleRepository;
    }
}
