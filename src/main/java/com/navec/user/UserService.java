package com.navec.user;

import com.navec.config.JwtService;
import com.navec.config.UserPrincipal;
import com.navec.environment.Env;
import com.navec.exception.ResponseException;
import com.navec.notifications.HtmlNotificationService;
import com.navec.auth.request.LoginRequestDto;
import com.navec.auth.request.RegisterRequestDto;
import com.navec.auth.response.UserResponseDto;
import com.navec.user.activations.Activation;
import com.navec.user.activations.ActivationRepository;
import com.navec.user.profile.Profile;
import com.navec.user.request.ActivateUserRequestDto;
import com.navec.utils.PermissionUtils;
import com.navec.utils.TimestampUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final ActivationRepository activationRepository;

    private final Env env;


    private final HtmlNotificationService htmlNotificationService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       ActivationRepository activationRepository,
                       Env env,
                       HtmlNotificationService htmlNotificationService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.activationRepository = activationRepository;
        this.env = env;
        this.htmlNotificationService = htmlNotificationService;
    }

    public UserResponseDto login(LoginRequestDto request) throws ResponseException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseException(HttpStatus.UNAUTHORIZED));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return getUserResponseDto(user);
        }

        throw new ResponseException(HttpStatus.UNAUTHORIZED);
    }

    public UserResponseDto register(RegisterRequestDto request) throws ResponseException, MessagingException, UnsupportedEncodingException {
        Optional<User> optionalUser = this.userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST);
        }

        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        profile.setUpdatedAt(TimestampUtils.getCurrentTimestamp());

        // Create user
        User user = new User();
        user.setActive(false);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        user.setUpdatedAt(TimestampUtils.getCurrentTimestamp());

        user.setProfile(profile);
        profile.setUser(user);

        User newUser = userRepository.save(user);

        if (Boolean.TRUE.equals(this.env.getShouldSendEmail())) {
            this.htmlNotificationService.activateUser(newUser, createNewActivation(newUser));
        }

        return getUserResponseDto(newUser);
    }

    public void activateUser(final ActivateUserRequestDto activateUserRequestDto, Long pathUserId) throws ResponseException {
        if(PermissionUtils.isMissingPermission(this.getCurrentUser(), pathUserId)) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        User user = getUpdatableUser(pathUserId);

        Optional<Activation> activationOptional = activationRepository.findByTokenAndUser(activateUserRequestDto.getToken(), user);

        if (activationOptional.isEmpty()) {
            log.error("Trying to activate user with unknown token. user:" + user.getId() + ", token: " + activateUserRequestDto.getToken());
            throw new ResponseException(HttpStatus.BAD_REQUEST);
        }

        user.setActive(true);
        user.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        userRepository.save(user);
        List<Activation> activations = activationRepository.findAllByUser(user);

        activationRepository.deleteAllById(
                activations.stream()
                        .map(Activation::getId)
                        .toList()
        );
    }

    public void resendActivation(Long pathUserId) throws ResponseException, MessagingException, UnsupportedEncodingException {
        User user = getUpdatableUser(pathUserId);
        List<Activation> activationList = activationRepository.findAllByUser(user);

        if (activationList.size() >= 3 && Boolean.TRUE.equals(!user.getActive())) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }
        Activation activation = createNewActivation(user);
        activationRepository.save(activation);

        if(Boolean.TRUE.equals(this.env.getShouldSendEmail())) {
            this.htmlNotificationService.activateUser(user, activation);
        }

    }

    private static Activation createNewActivation(User user) {
        final String token = UUID.randomUUID().toString();

        Activation activation = new Activation();
        activation.setUser(user);
        activation.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        activation.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        activation.setToken(token);
        activation.setActiveUntil(TimestampUtils.getTimestampIn24Hrs());
        return activation;
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserResponseDto getUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(jwtService.generateToken(user))
                .updatedAt(user.getUpdatedAt().toString())
                .createdAt(user.getCreatedAt().toString())
                .role(user.getRole())
                .build();
    }

    public User getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Object userPrincipal = auth.getPrincipal();
        return ((UserPrincipal) userPrincipal).getUser();
    }

    public User findByIdOrFail(Long pathUserId) {
        return this.userRepository.findById(pathUserId)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND));
    }

    public User getUpdatableUser(Long pathUserId) {
        User authUser = this.getCurrentUser();

        if (!Objects.equals(authUser.getId(), pathUserId)) {
            return this.findByIdOrFail(pathUserId);
        }

        return authUser;
    }

    public void deleteUser(Long pathUserId) {
        if(PermissionUtils.isMissingPermission(this.getCurrentUser(), pathUserId)) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        User workUser = this.getUpdatableUser(pathUserId);

        userRepository.deleteById(workUser.getId());

    }
}
