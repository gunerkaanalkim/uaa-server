package org.kaanalkim.authserver.service.impl;

import org.kaanalkim.authserver.model.OperationType;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.UserToken;
import org.kaanalkim.authserver.repository.UserTokenRepository;
import org.kaanalkim.authserver.service.UserTokenService;
import org.kaanalkim.common.exception.ActiveTokenFoundException;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserTokenServiceImpl extends AbstractCrudService<UserToken> implements UserTokenService {
    private final UserTokenRepository userTokenRepository;
    private final int userTokenExpirationWindow;

    @Override
    @SuppressWarnings("unchecked")
    protected UserTokenRepository getRepository() {
        return this.userTokenRepository;
    }

    public UserTokenServiceImpl(UserTokenRepository userTokenRepository, @Value("${userToken.expiration-window}") int userTokenExpirationWindow) {
        this.userTokenRepository = userTokenRepository;
        this.userTokenExpirationWindow = userTokenExpirationWindow;
    }

    @Override
    public UserToken createUserToken(User user, OperationType operationType) throws ActiveTokenFoundException {
        Optional<UserToken> existingUserToken = this.userTokenRepository
                .findByUserAndOperationTypeAndExpireDateGreaterThan(user, operationType, new Date());

        if (existingUserToken.isPresent()) {
            throw new ActiveTokenFoundException("There is an active token found.");
        }

        UserToken userToken = UserToken.builder()
                .operationType(operationType)
                .user(user)
                .expireDate(setExpirationDateTime())
                .token(UUID.randomUUID().toString())
                .build();

        userTokenRepository.save(userToken);

        return userToken;
    }

    private Date setExpirationDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, this.userTokenExpirationWindow);

        return calendar.getTime();
    }
}
