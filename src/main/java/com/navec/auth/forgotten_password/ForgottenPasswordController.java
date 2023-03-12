package com.navec.auth.forgotten_password;

import com.navec.exception.ResponseException;
import com.navec.auth.request.ForgottenPasswordRequestDto;
import com.navec.auth.request.ResetPasswordRequestDto;
import com.navec.auth.response.UserResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/auth/forgotten-password")
public class ForgottenPasswordController {
    private final ForgottenPasswordService forgottenPasswordService;

    public ForgottenPasswordController(final ForgottenPasswordService forgottenPasswordService) {
        this.forgottenPasswordService = forgottenPasswordService;
    }

    @PostMapping("")
    public ResponseEntity<Object> index(
            @Valid @RequestBody ForgottenPasswordRequestDto request
    ) throws MessagingException, UnsupportedEncodingException {
        try {
            this.forgottenPasswordService.forgottenPassword(request);
            return ResponseEntity.status(200).build();
        } catch(ResponseException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping( path = "/reset")
    public ResponseEntity<UserResponseDto> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDto requestDto
    ) {
        try {
            UserResponseDto userResponseDto = this.forgottenPasswordService.resetPassword(requestDto);
            return ResponseEntity.ok(userResponseDto);
        } catch(ResponseException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
