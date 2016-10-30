package org.gluu.message.consumer.domain.log4j;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by eugeniuparvan on 10/30/16.
 */
@Entity
@Table(name = "oxauth_server_logging_event")
public class OXAuthServerLoggingEvent extends LoggingEventBase<OXAuthServerLoggingEventException> {
}
