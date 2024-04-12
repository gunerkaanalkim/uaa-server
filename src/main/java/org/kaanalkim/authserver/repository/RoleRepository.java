package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.common.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
}
