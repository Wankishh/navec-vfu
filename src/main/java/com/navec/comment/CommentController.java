package com.navec.comment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments")
@SecurityRequirement(name = "apiAuth")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(name = "apiAuth")
    @PostMapping("/{listingId}")
    public ResponseEntity<Object> createComment(@RequestBody CreateCommentRequest createCommentRequest,
                                                @PathVariable Long listingId) {
        this.commentService.createComment(createCommentRequest, listingId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
