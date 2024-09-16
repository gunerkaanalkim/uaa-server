package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.model.OperationType;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.UserToken;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.request.ForgotPasswordRequest;
import org.kaanalkim.authserver.payload.response.ForgotPasswordResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.common.exception.ActiveTokenFoundException;
import org.kaanalkim.common.service.base.BaseCrudService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserTokenService extends BaseCrudService<UserToken> {
   UserToken createUserToken(User user, OperationType operationType) throws ActiveTokenFoundException;

}
