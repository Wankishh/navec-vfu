package com.navec.user;

import com.navec.exception.ResponseException;
import com.navec.user.request.ActivateUserRequestDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/{user_id}/activate")
    public ResponseEntity<Object> activateUser(
            @Valid @RequestBody ActivateUserRequestDto activateUserRequestDto,
            @PathVariable("user_id") Long pathUserId) {
        try {
            userService.activateUser(activateUserRequestDto, pathUserId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }

    @GetMapping(path = "/{user_id}/resend-activation")
    public ResponseEntity<Object> resendActivation(
            @PathVariable("user_id") Long pathUserId
    ) throws MessagingException, UnsupportedEncodingException {
        try {
            userService.resendActivation(pathUserId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("user_id") Long pathUserId
    ) {
        try {
            userService.deleteUser(pathUserId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }
}
