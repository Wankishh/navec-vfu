package com.navec.environment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class EnvImpl implements Env {
    @Value("${config.send_email:true}")
    private Boolean shouldSendEmails;

    @Value("${config.image_uri:http://localhost/static/}")
    private String getBaseImageUri;

    @Override
    public Boolean getShouldSendEmail() {
        return shouldSendEmails;
    }

    public String getGetBaseImageUri() {
        return getBaseImageUri;
    }
}
