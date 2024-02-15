package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.payload.dto.HasPermissionDTO;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Optional<Permission> findByControllerAndPermissionName(String controller, String permissionName);

    @Query(nativeQuery = true,
            value = "SELECT p.id,\n" +
                    "       p.controller,\n" +
                    "       p.controller_alias              as controllerAlias,\n" +
                    "       p.permission_name               as permissionName,\n" +
                    "       p.description,\n" +
                    "       COALESCE((SELECT rp.id as rolePermissionId\n" +
                    "        FROM role_permission rp\n" +
                    "        where rp.role_id = ?1\n" +
                    "          and rp.permission_id = p.id), 0) as hasPermission\n" +
                    "FROM permission p")
    List<HasPermissionDTO> getAllWithHasPermission(Long roleId);
}
