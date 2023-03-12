package com.navec.auth;


import com.navec.user.UserService;
import com.navec.exception.ResponseException;
import com.navec.auth.request.LoginRequestDto;
import com.navec.auth.request.RegisterRequestDto;
import com.navec.auth.response.UserResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/auth")
@Slf4j
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch(ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }

    @PostMapping(path = "register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) throws MessagingException, UnsupportedEncodingException {
        try {
            return ResponseEntity.ok(userService.register(request));
        } catch (ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }

    @GetMapping(path = "currentUser")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getUserResponseDto(userService.getCurrentUser()));
    }
}
