package com.navec.environment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class EnvImpl implements Env {
    @Value("${config.send_email:false}")
    private Boolean shouldSendEmails;

    @Override
    public Boolean getShouldSendEmail() {
        return shouldSendEmails;
    }
}
