package com.navec.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<Object> createComment(@RequestBody CreateCommentRequest createCommentRequest,
                                                @PathVariable Long listingId) {
        this.commentService.createComment(createCommentRequest, listingId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
