package org.gluu.message.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
@ConfigurationProperties(prefix = "message-consumer", ignoreUnknownFields = false)
public class MessageConsumerProperties {

    private final BaseProperties oauth2_audit = new BaseProperties();

    private final BaseProperties oxauth_server = new BaseProperties();

    public BaseProperties getOauth2_audit() {
        return oauth2_audit;
    }

    public BaseProperties getOxauth_server() {
        return oxauth_server;
    }

    public static class BaseProperties {

        private String destination;

        private int clear_logs_older_than;

        private String cron_for_log_cleaner;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getClear_logs_older_than() {
            return clear_logs_older_than;
        }

        public void setClear_logs_older_than(int clear_logs_older_than) {
            this.clear_logs_older_than = clear_logs_older_than;
        }

        public String getCron_for_log_cleaner() {
            return cron_for_log_cleaner;
        }

        public void setCron_for_log_cleaner(String cron_for_log_cleaner) {
            this.cron_for_log_cleaner = cron_for_log_cleaner;
        }
    }

}
