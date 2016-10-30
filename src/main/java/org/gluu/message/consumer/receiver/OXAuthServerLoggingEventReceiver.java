package org.gluu.message.consumer.receiver;

import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
public class OXAuthServerLoggingEventReceiver {

    private static final Logger log = LoggerFactory.getLogger(OXAuthServerLoggingEventReceiver.class);

    @JmsListener(destination = "${message-consumer.oxauth-server}")
    public void onMessage(LoggingEvent loggingEvent) {
        log.info("Message from oxauth.server: " + loggingEvent.getMessage());
    }
}
