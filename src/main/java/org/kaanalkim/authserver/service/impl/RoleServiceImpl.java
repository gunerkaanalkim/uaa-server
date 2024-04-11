package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.mapper.RoleMapper;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.payload.dto.RoleDTO;
import org.kaanalkim.authserver.repository.RoleRepository;
import org.kaanalkim.authserver.service.RoleService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends AbstractCrudService<Role, RoleDTO> implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

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
