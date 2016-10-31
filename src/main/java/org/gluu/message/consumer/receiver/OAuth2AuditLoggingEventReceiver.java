package org.gluu.message.consumer.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluu.message.consumer.domain.OAuth2AuditLoggingEvent;
import org.gluu.message.consumer.repository.OAuth2AuditLoggingEventRepository;
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
public class OAuth2AuditLoggingEventReceiver {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuditLoggingEventReceiver.class);

    @Inject
    private OAuth2AuditLoggingEventRepository repository;

    @Inject
    private ObjectMapper objectMapper;

    @JmsListener(destination = "${message-consumer.oauth2-audit.destination}")
    public void onMessage(String message) {
        try {
            OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent = objectMapper.readValue(message, OAuth2AuditLoggingEvent.class);
            oAuth2AuditLoggingEvent = repository.save(oAuth2AuditLoggingEvent);
            log.info("added oAuth2AuditLoggingEvent with id: " + oAuth2AuditLoggingEvent.getId());
        } catch (IOException e) {
            log.error("Could not deserialize the message.", e);
        }
    }
}
