package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.OperationType;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.UserToken;
import org.kaanalkim.common.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends BaseRepository<UserToken, Long> {
    Optional<UserToken> findByUserAndOperationTypeAndExpireDateGreaterThan(User user, OperationType operationType, Date now);
}
