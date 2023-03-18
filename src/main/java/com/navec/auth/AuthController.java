package com.navec.auth;


import com.navec.user.UserService;
import com.navec.auth.request.LoginRequestDto;
import com.navec.auth.request.RegisterRequestDto;
import com.navec.auth.response.UserResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/auth")
@Slf4j
@Tag(name = "Auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping(path = "register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @GetMapping(path = "currentUser")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getUserResponseDto(userService.getCurrentUser()));
    }
}
