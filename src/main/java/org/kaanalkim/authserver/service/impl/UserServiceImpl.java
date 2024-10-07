package org.kaanalkim.authserver.service.impl;

import lombok.AllArgsConstructor;
import org.kaanalkim.authserver.exception.UserAlreadyExistException;
import org.kaanalkim.authserver.model.OperationType;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.model.UserToken;
import org.kaanalkim.authserver.payload.dto.UserDTO;
import org.kaanalkim.authserver.payload.request.ChangePassword;
import org.kaanalkim.authserver.payload.request.EmailDetails;
import org.kaanalkim.authserver.payload.request.ForgotPasswordRequest;
import org.kaanalkim.authserver.payload.response.ForgotPasswordResponse;
import org.kaanalkim.authserver.payload.response.UserInfo;
import org.kaanalkim.authserver.repository.UserRepository;
import org.kaanalkim.authserver.service.EmailService;
import org.kaanalkim.authserver.service.UserService;
import org.kaanalkim.authserver.service.UserTokenService;
import org.kaanalkim.common.exception.ActiveTokenFoundException;
import org.kaanalkim.common.exception.NotFoundException;
import org.kaanalkim.common.exception.UserNotFoundException;
import org.kaanalkim.common.service.base.AbstractCrudService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserTokenService userTokenService;

    @Override
    @SuppressWarnings("unchecked")
    protected UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService,
                           UserTokenService userTokenService,
                           @Value("${ui.base-url}") String baseURL,
                           @Value("${ui.reset-password}") String resetPassword) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userTokenService = userTokenService;
    }

    @Override
    public User changePassword(ChangePassword changePassword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> oldUser = this.userRepository.findByUsername(userName);

        if (oldUser.isEmpty()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        User user = oldUser.get();

        if (changePassword.getPassword().equals(changePassword.getRePassword())) {
            user.setPassword(changePassword.getPassword());
        }

        this.userRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    @Override
    public UserInfo getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public void isUserExist(UserDTO userDTO) {
        Optional<User> userByRealm = this.userRepository
                .findUserByUsernameAndRealmId(userDTO.getUsername(), userDTO.getRealm().getId());

        if (userByRealm.isPresent()) {
            throw new UserAlreadyExistException("User cannot be registered with given username.");
        }
    }

    @Override
    public User findUserByUsernameAndRealmId(String username, long realmId) throws UserNotFoundException {
        Optional<User> userByRealm = this.userRepository
                .findUserByUsernameAndRealmId(username, realmId);

        if (userByRealm.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        return userByRealm.get();
    }

    @Override
    public void sendRegistrationEmail(UserDTO userDTO) {
        Context context = new Context();
        context.setVariable("username", userDTO.getUsername());
        context.setVariable("name", userDTO.getName());
        context.setVariable("surname", userDTO.getSurname());

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(Objects.requireNonNull(userDTO).getEmail())
                .subject("User has been created")
                .build();

        this.emailService.sendWithTemplate(emailDetails, "user-registration-en", context);
    }

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest)
            throws UserNotFoundException, ActiveTokenFoundException {
        //Find user by realm
        Optional<User> userByRealm = this.userRepository
                .findUserByUsernameAndRealmId(forgotPasswordRequest.getUsername(), forgotPasswordRequest.getRealmId());

        if (userByRealm.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        //Create user token
        UserToken userToken = this.userTokenService
                .createUserToken(userByRealm.get(), OperationType.FORGOT_PASSWORD);

        //  Forgot password email
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(Objects.requireNonNull(userByRealm.get()).getEmail())
                .subject("Forgot Password")
                .build();

        Context context = new Context();
        context.setVariable("username", userByRealm.get().getUsername());
        context.setVariable("name", userByRealm.get().getName());
        context.setVariable("surname", userByRealm.get().getSurname());
        context.setVariable("surname", userByRealm.get().getSurname());

        this.emailService.sendWithTemplate(emailDetails, "forgot-password-en", context);

        return ForgotPasswordResponse.builder()
                .timeout(userToken.getExpireDate())
                .build();
    }

    private static void sendForgotPasswordEmail() {

    }
}
