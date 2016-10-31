package org.gluu.message.consumer.schedule;

import org.gluu.message.consumer.repository.OAuth2AuditLoggingEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/31/16.
 */
@Component
public class OAuth2AuditScheduler extends BaseScheduler<OAuth2AuditLoggingEventRepository> {

    @Override
    int getDaysAfterYouCanDeleteLogs() {
        return messageConsumerProperties.getOauth2_audit().getDays_after_logs_can_be_deleted();
    }

    @Scheduled(cron = "${message-consumer.oauth2-audit.cron-for-log-cleaner}")
    public void deleteLogs() {
        super.deleteLogs();
    }
}