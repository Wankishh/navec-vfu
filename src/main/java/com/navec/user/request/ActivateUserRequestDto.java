package com.navec.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivateUserRequestDto {

    @NotNull
    @NotEmpty

    private String token;
}
