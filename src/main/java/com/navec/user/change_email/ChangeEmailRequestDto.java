package com.navec.user.change_email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeEmailRequestDto {
    @NotBlank
    @Email
    private String newEmail;

    @NotBlank
    @Email
    private String confirmNewEmail;
}
