package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.RolePermission;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission, Long> {
    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
    void deleteAllByRole(Role role);
    List<RolePermission> findAllByRole(Role role);
}
