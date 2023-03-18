package com.navec.user.change_password;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{user_id}/change-password")
@Tag(name = "ChangePassword")
@SecurityRequirement(name = "apiAuth")
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    public ChangePasswordController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @PutMapping("")
    public ResponseEntity<Object> changePassword(
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto,
            @PathVariable("user_id") Long pathUserId) {
        this.changePasswordService.changePassword(changePasswordRequestDto, pathUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
