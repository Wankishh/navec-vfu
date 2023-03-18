package com.navec.contact;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/api/v1/contacts")
@Tag(name = "Contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Object> sendContact(@Valid @RequestBody ContactRequestDto contactRequestDto) throws MessagingException, UnsupportedEncodingException {
        this.contactService.sendContact(contactRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
