package com.navec.contact;

import com.navec.environment.Env;
import com.navec.notifications.HtmlNotificationService;
import com.navec.utils.TimestampUtils;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final Env env;
    private final HtmlNotificationService htmlNotificationService;

    public ContactService(ContactRepository contactRepository, Env env, HtmlNotificationService htmlNotificationService) {
        this.contactRepository = contactRepository;
        this.env = env;
        this.htmlNotificationService = htmlNotificationService;
    }

    public void sendContact(ContactRequestDto contactRequestDto) throws MessagingException, UnsupportedEncodingException {
        Contact contact = new Contact();
        contact.setName(contactRequestDto.getName());
        contact.setEmail(contactRequestDto.getEmail());
        contact.setPhone(contactRequestDto.getEmail());
        contact.setMessage(contactRequestDto.getMessage());
        contact.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        contact.setUpdatedAt(TimestampUtils.getCurrentTimestamp());

        if(Boolean.TRUE.equals(this.env.getShouldSendEmail())) {
            this.htmlNotificationService.sendContactEmail(contact);
        }
        this.contactRepository.save(contact);
    }
}
