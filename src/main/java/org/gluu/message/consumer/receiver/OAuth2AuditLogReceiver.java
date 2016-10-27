package org.gluu.message.consumer.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluu.message.consumer.domain.OAuth2AuditLog;
import org.gluu.message.consumer.service.OAuth2AudigLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
public class OAuth2AuditLogReceiver {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuditLogReceiver.class);

    @Inject
    private OAuth2AudigLogService service;

    @Inject
    private ObjectMapper objectMapper;

    @JmsListener(destination = "oauth2.audit.logging")
    public void receiveQueue(String message) {
        try {
            OAuth2AuditLog oAuth2AuditLog = objectMapper.readValue(message, OAuth2AuditLog.class);
            Long id = service.add(oAuth2AuditLog);
            log.info("added oAuth2AuditLog with id: " + id);
        } catch (IOException e) {
            log.error("Receiving message error.", e);
        }
    }

}
