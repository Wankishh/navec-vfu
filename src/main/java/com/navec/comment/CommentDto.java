package com.navec.comment;

import com.navec.auth.response.UserResponseDto;
import com.navec.user.UserDto;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private UserDto user;
    private String comment;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.user = new UserDto(comment.getUser());
        this.comment = comment.getComment();
    }
}
