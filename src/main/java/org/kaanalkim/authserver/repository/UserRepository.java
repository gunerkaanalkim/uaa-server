package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserInfo getByUsername(String username);
}
