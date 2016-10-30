package org.gluu.message.consumer.domain.log4j;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Created by eugeniuparvan on 10/30/16.
 */

@Entity
@Table(name = "oxauth_server_logging_event_exception")
@AssociationOverride(name = "loggingEvent",
        joinColumns = @JoinColumn(name = "oxauth_server_logging_event_id"))
public class OXAuthServerLoggingEventException extends LoggingEventExceptionBase<OXAuthServerLoggingEvent> {
}
