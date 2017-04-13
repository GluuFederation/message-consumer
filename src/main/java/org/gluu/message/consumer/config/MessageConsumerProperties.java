package org.gluu.message.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
@ConfigurationProperties(prefix = "message-consumer", ignoreUnknownFields = false)
public class MessageConsumerProperties {

    private String logger_path = "";

    private final BaseProperties oauth2_audit = new BaseProperties();

    private final BaseProperties oxauth_server = new BaseProperties();

    public String getLogger_path() {
        return logger_path;
    }

    public BaseProperties getOauth2_audit() {
        return oauth2_audit;
    }

    public void setLogger_path(String logger_path) {
        this.logger_path = logger_path;
    }

    public BaseProperties getOxauth_server() {
        return oxauth_server;
    }

    public static class BaseProperties {

        private String destination;

        private int days_after_logs_can_be_deleted;

        private String cron_for_log_cleaner;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getDays_after_logs_can_be_deleted() {
            return days_after_logs_can_be_deleted;
        }

        public void setDays_after_logs_can_be_deleted(int days_after_logs_can_be_deleted) {
            this.days_after_logs_can_be_deleted = days_after_logs_can_be_deleted;
        }

        public String getCron_for_log_cleaner() {
            return cron_for_log_cleaner;
        }

        public void setCron_for_log_cleaner(String cron_for_log_cleaner) {
            this.cron_for_log_cleaner = cron_for_log_cleaner;
        }
    }

}
