package org.gluu.message.consumer.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.gluu.message.consumer.config.util.Constants;
import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEvent;
import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEventException;
import org.gluu.message.consumer.repository.OXAuthServerLoggingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
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

    private static final Logger logger = LoggerFactory.getLogger(OXAuthServerLoggingEventReceiver.class);

    @Inject
    private Environment environment;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private OXAuthServerLoggingEventRepository repository;

    @JmsListener(destination = "${message-consumer.oxauth-server.destination}")
    public void onMessage(Log4jLogEvent loggingEvent) {
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent = new OXAuthServerLoggingEvent();
        oxAuthServerLoggingEvent.setLevel(loggingEvent.getLevel().toString());
        oxAuthServerLoggingEvent.setLoggerName(loggingEvent.getLoggerName());
        oxAuthServerLoggingEvent.setTimestamp(new Date(loggingEvent.getTimeMillis()));
        oxAuthServerLoggingEvent.setFormattedMessage(loggingEvent.getMessage().toString());

        ThrowableProxy throwableInformation = loggingEvent.getThrownProxy();
        if (throwableInformation != null && throwableInformation.getExtendedStackTrace().length > 0) {
            List<OXAuthServerLoggingEventException> exceptions = new ArrayList<>();
            int index = 0;
            OXAuthServerLoggingEventException oxAuthServerLoggingEventException = new OXAuthServerLoggingEventException();
            oxAuthServerLoggingEventException.setIndex(index++);
            oxAuthServerLoggingEventException.setTraceLine(throwableInformation.toString());
            oxAuthServerLoggingEventException.setLoggingEvent(oxAuthServerLoggingEvent);
            exceptions.add(oxAuthServerLoggingEventException);
            for (ExtendedStackTraceElement stackTraceElement : throwableInformation.getExtendedStackTrace()) {
                oxAuthServerLoggingEventException = new OXAuthServerLoggingEventException();
                oxAuthServerLoggingEventException.setIndex(index++);
                oxAuthServerLoggingEventException.setTraceLine(stackTraceElement.toString());
                oxAuthServerLoggingEventException.setLoggingEvent(oxAuthServerLoggingEvent);
                exceptions.add(oxAuthServerLoggingEventException);
            }
            oxAuthServerLoggingEvent.setExceptions(exceptions);
        }
        if (environment.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)){
            try {
                logger.info(objectMapper.writeValueAsString(oxAuthServerLoggingEvent));
            } catch (JsonProcessingException e) {
                logger.error("Can't parse oxAuthServerLoggingEvent", e);
            }
        }
        repository.save(oxAuthServerLoggingEvent);
    }
}
