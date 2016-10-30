package org.gluu.message.consumer.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Entity
@Table(name = "oauth2_audit_logging_event")
public class OAuth2AuditLoggingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ip;

    private String action;

    private String clientId;

    private String username;

    private String scope;

    private Boolean success;

    private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OAuth2AuditLoggingEvent{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", action='" + action + '\'' +
                ", clientId='" + clientId + '\'' +
                ", username='" + username + '\'' +
                ", scope='" + scope + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                '}';
    }
}
