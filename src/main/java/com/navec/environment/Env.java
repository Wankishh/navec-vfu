package com.navec.environment;

public interface Env {
    Boolean getShouldSendEmail();
    String getBaseImageUri();

    String getAdminEmail();
}
