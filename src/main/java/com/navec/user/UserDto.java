package com.navec.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
