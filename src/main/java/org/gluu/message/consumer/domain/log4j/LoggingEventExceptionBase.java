package org.gluu.message.consumer.domain.log4j;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by eugeniuparvan on 10/30/16.
 */

@MappedSuperclass
public class LoggingEventExceptionBase<T extends LoggingEventBase> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "\"index\"")
    private Integer index;

    @Column(columnDefinition = "text")
    private String traceLine;

    @ManyToOne
    @JsonIgnore
    private T loggingEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTraceLine() {
        return traceLine;
    }

    public void setTraceLine(String traceLine) {
        this.traceLine = traceLine;
    }

    public T getLoggingEvent() {
        return loggingEvent;
    }

    public void setLoggingEvent(T loggingEvent) {
        this.loggingEvent = loggingEvent;
    }

    @Override
    public String toString() {
        return "LoggingEventExceptionBase{" +
                "id=" + id +
                ", traceLine=" + traceLine +
                ", traceLine='" + traceLine + '\'' +
                '}';
    }
}
