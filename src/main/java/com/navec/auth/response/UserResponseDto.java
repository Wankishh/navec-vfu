package com.navec.auth.response;

import com.navec.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;

    private String createdAt;
    private String updatedAt;

    private String token;

    private Role role;
}
