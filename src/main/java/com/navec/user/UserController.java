package com.navec.user;

import com.navec.listing.ListingService;
import com.navec.listing.response.PreviewListing;
import com.navec.user.request.ActivateUserRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/users")
@Tag(name = "User")
public class UserController {
    private final UserService userService;
    private final ListingService listingsService;

    public UserController(UserService userService,
                          ListingService listingsService) {
        this.userService = userService;
        this.listingsService = listingsService;
    }

    @PostMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/{user_id}/activate")
    public ResponseEntity<Object> activateUser(
            @Valid @RequestBody ActivateUserRequestDto activateUserRequestDto,
            @PathVariable("user_id") Long pathUserId) {
        userService.activateUser(activateUserRequestDto, pathUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/{user_id}/resend-activation")
    public ResponseEntity<Object> resendActivation(
            @PathVariable("user_id") Long pathUserId
    ) throws MessagingException, UnsupportedEncodingException {
        userService.resendActivation(pathUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{user_id}")
    @SecurityRequirement(name = "apiAuth")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("user_id") Long pathUserId
    ) {
        userService.deleteUser(pathUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userId}/listings")
    public ResponseEntity<List<PreviewListing>> getUserListings(@PathVariable("userId") Long userId) {
       return ResponseEntity.ok(this.listingsService.getUserListings(userId));
    }
}
