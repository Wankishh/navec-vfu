package com.navec.user.change_password;

import com.navec.exception.ResponseException;
import com.navec.user.User;
import com.navec.user.UserRepository;
import com.navec.user.UserService;
import com.navec.utils.PermissionUtils;
import com.navec.utils.TimestampUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final UserRepository userRepository;

    public ChangePasswordService(
            PasswordEncoder passwordEncoder,
            UserService userService,
            UserRepository userRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto, Long pathUserId) throws ResponseException {
        if(PermissionUtils.isMissingPermission(this.userService.getCurrentUser(), pathUserId)) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        User user = this.userService.getUpdatableUser(pathUserId);

        boolean isSamePassword = this.passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), user.getPassword());
        boolean isNewPasswordConfirmed = changePasswordRequestDto.getNewPassword().equals(
                changePasswordRequestDto.getConfirmNewPassword()
        );
        boolean isOldPasswordAsNewPassword = this.passwordEncoder.matches(
                changePasswordRequestDto.getNewPassword(),
                user.getPassword()
        );

        if(!isNewPasswordConfirmed || isOldPasswordAsNewPassword) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!isSamePassword) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        user.setPassword(this.passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        user.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        userRepository.save(user);
    }
}
