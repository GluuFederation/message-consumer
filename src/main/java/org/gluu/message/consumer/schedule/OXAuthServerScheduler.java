package org.gluu.message.consumer.schedule;

import org.gluu.message.consumer.repository.OXAuthServerLoggingEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/31/16.
 */
@Component
public class OXAuthServerScheduler extends BaseScheduler<OXAuthServerLoggingEventRepository> {

    @Override
    public int getDaysAfterYouCanDeleteLogs() {
        return messageConsumerProperties.getOxauth_server().getDays_after_logs_can_be_deleted();
    }

    @Scheduled(cron = "${message-consumer.oxauth-server.cron-for-log-cleaner}")
    public void deleteLogs() {
        super.deleteLogs();
    }
}
