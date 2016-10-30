package org.gluu.message.consumer.domain.log4j;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by eugeniuparvan on 10/30/16.
 */

@MappedSuperclass
public class LoggingEventBase<T extends LoggingEventExceptionBase> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date timestamp;

    private String formattedMessage;

    private String loggerName;

    private String level;

    @OneToMany(mappedBy = "loggingEvent", cascade = CascadeType.ALL)
    private Set<T> exceptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }

    public void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<T> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Set<T> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public String toString() {
        return "LoggingEventBase{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", formattedMessage='" + formattedMessage + '\'' +
                ", loggerName='" + loggerName + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
