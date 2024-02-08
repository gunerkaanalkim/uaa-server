package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.repository.RoleRepository;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractCrudService<Role, RoleDTO> implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RoleRepository getRepository() {
        return this.roleRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RoleMapper getMapper() {
        return this.roleMapper;
    }
}
