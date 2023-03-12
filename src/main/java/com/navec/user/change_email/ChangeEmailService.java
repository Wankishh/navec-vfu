package com.navec.user.change_email;

import com.navec.auth.response.UserResponseDto;
import com.navec.exception.ResponseException;
import com.navec.user.User;
import com.navec.user.UserRepository;
import com.navec.user.UserService;
import com.navec.utils.PermissionUtils;
import com.navec.utils.TimestampUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangeEmailService {

    private final UserService userService;
    private final UserRepository userRepository;

    public ChangeEmailService(UserService userService,
                              UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public UserResponseDto changeEmail(ChangeEmailRequestDto changeEmailRequestDto, Long pathUserId) throws ResponseException {
        if(PermissionUtils.isMissingPermission(this.userService.getCurrentUser(), pathUserId)) {
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        User user = this.userService.getUpdatableUser(pathUserId);

        boolean isEmailConfirmed = changeEmailRequestDto.getNewEmail().equals(
                changeEmailRequestDto.getConfirmNewEmail()
        );

        if(user.getEmail().equals(changeEmailRequestDto.getNewEmail()) || !isEmailConfirmed) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<User> optionalUser = this.userService.findByEmail(changeEmailRequestDto.getNewEmail());

        if(optionalUser.isPresent()) {
            throw new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setEmail(changeEmailRequestDto.getNewEmail());
        user.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        userRepository.save(user);

        return this.userService.getUserResponseDto(user);
    }
}
