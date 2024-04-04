package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.HasPermissionDTO;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.mapper.PermissionMapper;
import org.kaanalkim.authserver.repository.PermissionRepository;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl extends AbstractCrudService<Permission, PermissionDTO> implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected PermissionRepository getRepository() {
        return this.permissionRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected PermissionMapper getMapper() {
        return this.permissionMapper;
    }

    @Override
    public Optional<Permission> findByControllerAndPermissionName(String controller, String permissionName) {
        return this.permissionRepository.findByControllerAndTitle(controller, permissionName);
    }

    @Override
    public List<HasPermissionDTO> getAllWithHasPermission(Long roleId) {
        return this.permissionRepository.getAllWithHasPermission(roleId);
    }
}
