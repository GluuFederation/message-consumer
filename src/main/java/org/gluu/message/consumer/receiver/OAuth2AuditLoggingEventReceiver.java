package org.gluu.message.consumer.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluu.message.consumer.config.condition.ProductionCondition;
import org.gluu.message.consumer.domain.OAuth2AuditLoggingEvent;
import org.gluu.message.consumer.repository.OAuth2AuditLoggingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
public class OAuth2AuditLoggingEventReceiver {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuditLoggingEventReceiver.class);

    @Inject
    private Environment environment;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private OAuth2AuditLoggingEventRepository repository;

    @JmsListener(destination = "${message-consumer.oauth2-audit.destination}")
    public void onMessage(String message) {
        try {
            OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent = objectMapper.readValue(message, OAuth2AuditLoggingEvent.class);
            repository.save(oAuth2AuditLoggingEvent);

            if ("true".equals(environment.getProperty(ProductionCondition.ENABLE_LOGGING)))
                logger.info(message.replace("\n", "").replace("\r", ""));
        } catch (IOException e) {
            logger.error("Could not deserialize the message.", e);
        }
    }
}
