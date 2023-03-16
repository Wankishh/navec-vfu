package com.navec.comment;

import com.navec.exception.ResponseException;
import com.navec.listing.Listing;
import com.navec.listing.ListingService;
import com.navec.user.User;
import com.navec.user.UserService;
import com.navec.utils.TimestampUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public static final int TOTAL_COMMENTS_PER_USER = 2;
    public static final String TOO_MANY_COMMENTS_ERROR = "Too many comments";
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final ListingService listingService;

    public CommentService(UserService userService,
                          CommentRepository commentRepository,
                          ListingService listingService) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.listingService = listingService;
    }

    public void createComment(CreateCommentRequest createCommentRequest, Long listingId) {
        Listing listing = this.listingService.findListingById(listingId);
        User user = this.userService.getCurrentUser();
        long commentsCreatedByCurrentUser = this.commentRepository.countByUserId(user.getId());

        if(commentsCreatedByCurrentUser >= TOTAL_COMMENTS_PER_USER) {
            throw new ResponseException(HttpStatus.FORBIDDEN, TOO_MANY_COMMENTS_ERROR);
        }

        Comment newComment = new Comment();
        newComment.setComment(createCommentRequest.getComment());
        newComment.setUser(user);
        newComment.setListing(listing);
        newComment.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        newComment.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        this.commentRepository.save(newComment);
    }


}
