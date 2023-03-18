package com.navec.environment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class EnvImpl implements Env {
    @Value("${config.send_email:true}")
    private Boolean shouldSendEmails;

    @Value("${config.image_uri:http://localhost/static/}")
    private String baseImageUri;

    @Value("${config.admin_email:admin@navec.bg}")
    private String adminEmail;

    @Override
    public Boolean getShouldSendEmail() {
        return shouldSendEmails;
    }

    @Override
    public String getBaseImageUri() {
        return baseImageUri;
    }

    @Override
    public String getAdminEmail() {
        return adminEmail;
    }
}
