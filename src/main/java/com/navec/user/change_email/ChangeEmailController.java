package com.navec.user.change_email;

import com.navec.auth.response.UserResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users/{user_id}/change-email")
@Tag(name = "ChangeEmail")
@SecurityRequirement(name = "apiAuth")
public class ChangeEmailController {

    private final ChangeEmailService changeEmailService;

    public ChangeEmailController(ChangeEmailService changeEmailService) {
        this.changeEmailService = changeEmailService;
    }

    @PutMapping("")
    public ResponseEntity<UserResponseDto> changeEmail(
            @Valid @RequestBody ChangeEmailRequestDto changeEmailRequestDto,
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.changeEmailService.changeEmail(changeEmailRequestDto, userId));
    }
}
