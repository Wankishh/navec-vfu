package com.navec.auth.forgotten_password;

import com.navec.auth.request.ForgottenPasswordRequestDto;
import com.navec.auth.request.ResetPasswordRequestDto;
import com.navec.auth.response.UserResponseDto;
import com.navec.environment.Env;
import com.navec.user.User;
import com.navec.user.UserRepository;
import com.navec.user.UserService;
import com.navec.exception.ResponseException;
import com.navec.notifications.HtmlNotificationService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgottenPasswordService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final HtmlNotificationService htmlNotificationService;

    private final ForgottenPasswordRepository forgottenPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final Env env;

    public ForgottenPasswordService(UserService userService,
                                    UserRepository userRepository,
                                    HtmlNotificationService htmlNotificationService,
                                    ForgottenPasswordRepository forgottenPasswordRepository,
                                    PasswordEncoder passwordEncoder,
                                    Env env) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.htmlNotificationService = htmlNotificationService;
        this.forgottenPasswordRepository = forgottenPasswordRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    public void forgottenPassword(ForgottenPasswordRequestDto requestDto) throws ResponseException, MessagingException, UnsupportedEncodingException {
        Optional<User> optionalUser = this.userRepository.findByEmail(requestDto.getEmail());

        if(optionalUser.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST);
        }
        ForgottenPassword forgottenPassword = new ForgottenPassword();
        forgottenPassword.setUser(optionalUser.get());
        final String token = UUID.randomUUID().toString();
        forgottenPassword.setToken(token);
        forgottenPassword.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        forgottenPassword.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        this.forgottenPasswordRepository.save(forgottenPassword);
        if (Boolean.TRUE.equals(this.env.getShouldSendEmail())) {
            // TODO: Move to another thread
            this.htmlNotificationService.forgottenPassword(forgottenPassword);
        }
    }

    @Transactional
    public UserResponseDto resetPassword(ResetPasswordRequestDto requestDto) throws ResponseException {
        Optional<ForgottenPassword> forgottenPassword = forgottenPasswordRepository.findByToken(requestDto.getToken());

        boolean isSamePassword = requestDto.getNewPassword().equals(
                requestDto.getConfirmNewPassword()
        );

        if(forgottenPassword.isEmpty() || !isSamePassword) {
            throw new ResponseException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findById(forgottenPassword.get().getUser().getId());

        if(optionalUser.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST);
        }

        forgottenPasswordRepository.deleteByUser(optionalUser.get());

        User user = optionalUser.get();
        user.setPassword(this.passwordEncoder.encode(requestDto.getNewPassword()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        User savedUser = this.userRepository.save(user);

        return this.userService.getUserResponseDto(savedUser);
    }
}
