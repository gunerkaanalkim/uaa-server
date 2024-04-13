package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.common.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);

    UserInfo getByUsername(String username);

    Optional<User> findUserByUsernameAndRealmId(String username, long realmId);
}
