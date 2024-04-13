package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.common.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleUserRepository extends BaseRepository<RoleUser, Long> {
    Optional<RoleUser> findByUser(User user);
}
