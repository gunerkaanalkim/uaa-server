package org.kaanalkim.authserver.service.impl;

import java.util.List;
import java.util.Optional;

import org.kaanalkim.authserver.mapper.PermissionMapper;
import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.HasPermissionDTO;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.repository.PermissionRepository;
import org.kaanalkim.authserver.service.PermissionService;
import org.kaanalkim.authserver.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionServiceImpl extends AbstractCrudService<Permission, PermissionDTO> implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public Optional<Permission> findByControllerAndPermissionName(String controller, String permissionName) {
        return this.permissionRepository.findByControllerAndTitle(controller, permissionName);
    }

    @Override
    public List<HasPermissionDTO> getAllWithHasPermission(Long roleId) {
        return this.permissionRepository.getAllWithHasPermission(roleId);
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
}
