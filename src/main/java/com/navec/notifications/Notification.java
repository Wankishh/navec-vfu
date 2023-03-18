package com.navec.notifications;

import com.navec.auth.forgotten_password.ForgottenPassword;
import com.navec.contact.Contact;
import com.navec.user.User;
import com.navec.user.activations.Activation;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface Notification {
    void activateUser(User user, Activation activation) throws MessagingException, UnsupportedEncodingException;

    void forgottenPassword(ForgottenPassword forgottenPassword) throws MessagingException, UnsupportedEncodingException;
    void sendContactEmail(Contact contact) throws MessagingException, UnsupportedEncodingException;
}
