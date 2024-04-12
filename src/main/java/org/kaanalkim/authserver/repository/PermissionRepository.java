package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.Permission;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {
}
