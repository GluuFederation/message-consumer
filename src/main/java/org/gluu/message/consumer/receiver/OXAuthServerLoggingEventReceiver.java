package org.gluu.message.consumer.receiver;

import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEvent;
import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEventException;
import org.gluu.message.consumer.repository.OXAuthServerLoggingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
public class OXAuthServerLoggingEventReceiver {

    private static final Logger log = LoggerFactory.getLogger(OXAuthServerLoggingEventReceiver.class);

    @Inject
    private OXAuthServerLoggingEventRepository repository;

    @JmsListener(destination = "${message-consumer.oxauth-server.destination}")
    public void onMessage(Log4jLogEvent loggingEvent) {
        log.info("Message from oxauth.server: " + loggingEvent.getMessage());
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent = new OXAuthServerLoggingEvent();
        oxAuthServerLoggingEvent.setLevel(loggingEvent.getLevel().toString());
        oxAuthServerLoggingEvent.setLoggerName(loggingEvent.getLoggerName());
        oxAuthServerLoggingEvent.setTimestamp(new Date(loggingEvent.getTimeMillis()));
        oxAuthServerLoggingEvent.setFormattedMessage(loggingEvent.getMessage().toString());

        ThrowableProxy throwableInformation = loggingEvent.getThrownProxy();
        if (throwableInformation != null && throwableInformation.getExtendedStackTrace().length > 0) {
            List<OXAuthServerLoggingEventException> exceptions = new ArrayList<>();
            int index = 0;
            for (ExtendedStackTraceElement stackTraceElement : throwableInformation.getExtendedStackTrace()) {
                OXAuthServerLoggingEventException oxAuthServerLoggingEventException = new OXAuthServerLoggingEventException();
                oxAuthServerLoggingEventException.setIndex(index++);
                oxAuthServerLoggingEventException.setTraceLine(stackTraceElement.toString());
                oxAuthServerLoggingEventException.setLoggingEvent(oxAuthServerLoggingEvent);
                exceptions.add(oxAuthServerLoggingEventException);
            }
            oxAuthServerLoggingEvent.setExceptions(exceptions);
        }
        repository.save(oxAuthServerLoggingEvent);
    }
}
