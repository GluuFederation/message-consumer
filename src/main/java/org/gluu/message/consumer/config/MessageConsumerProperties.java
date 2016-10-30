package org.gluu.message.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
@ConfigurationProperties(prefix = "message-consumer", ignoreUnknownFields = false)
public class MessageConsumerProperties {
    
    private String oauth2_audit = "";

    private String oxauth_server = "";

    public String getOauth2_audit() {
        return oauth2_audit;
    }

    public void setOauth2_audit(String oauth2_audit) {
        this.oauth2_audit = oauth2_audit;
    }

    public String getOxauth_server() {
        return oxauth_server;
    }

    public void setOxauth_server(String oxauth_server) {
        this.oxauth_server = oxauth_server;
    }
}
