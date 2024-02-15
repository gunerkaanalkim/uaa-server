package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.HasPermissionDTO;
import org.kaanalkim.authserver.payload.dto.PermissionDTO;
import org.kaanalkim.authserver.service.base.BaseCrudService;

import java.util.List;
import java.util.Optional;

public interface PermissionService extends BaseCrudService<Permission, PermissionDTO> {
    Optional<Permission> findByControllerAndPermissionName(String controller, String permissionName);

    List<HasPermissionDTO> getAllWithHasPermission(Long roleId);
}
