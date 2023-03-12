package com.navec.notifications;

import com.navec.auth.forgotten_password.ForgottenPassword;
import com.navec.user.User;
import com.navec.user.activations.Activation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class HtmlNotificationService implements Notification {

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String mailFrom;

    @Value("${mail.from.name}")
    private String mailFromName;

    private final JavaMailSender mailSender;

    private final TemplateEngine htmlTemplateEngine;

    public HtmlNotificationService(JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    @Override
    public void activateUser(User user, Activation activation) throws MessagingException, UnsupportedEncodingException {
        String confirmationUrl = "https://google.bg?token=" + activation.getToken();

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email = getMimeMessageHelper(mimeMessage, user.getEmail(), "Registration Confirmation");

        final Context ctx = new Context();
        ctx.setVariable("email", user.getEmail());
        ctx.setVariable("name", user.getName());
        ctx.setVariable("url", confirmationUrl);

        final String htmlContent = this.htmlTemplateEngine.process("register", ctx);

        email.setText(htmlContent, true);
        mailSender.send(mimeMessage);

    }

    @Override
    public void forgottenPassword(ForgottenPassword forgottenPassword) throws MessagingException, UnsupportedEncodingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = getMimeMessageHelper(mimeMessage, forgottenPassword.getUser().getEmail(), "Forgotten Password");

        final Context ctx = new Context();
        ctx.setVariable("name", forgottenPassword.getUser().getName());
        ctx.setVariable("reset_url", "https://navec.bg/reset-password/" + forgottenPassword.getToken());

        final String htmlContent = this.htmlTemplateEngine.process("forgotten_password", ctx);

        messageHelper.setText(htmlContent, true);
        mailSender.send(mimeMessage);

    }

    private MimeMessageHelper getMimeMessageHelper(MimeMessage mimeMessage, String email, String subject) throws MessagingException, UnsupportedEncodingException {
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setFrom(new InternetAddress(mailFrom, mailFromName));
        return messageHelper;
    }
}
