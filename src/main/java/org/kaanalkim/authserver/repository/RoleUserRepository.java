package org.kaanalkim.authserver.repository;

import java.util.Optional;

import org.kaanalkim.authserver.model.RoleUser;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends BaseRepository<RoleUser, Long> {
    Optional<RoleUser> findByUser(User user);
}
