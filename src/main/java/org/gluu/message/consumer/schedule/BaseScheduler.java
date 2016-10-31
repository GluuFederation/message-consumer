package org.gluu.message.consumer.schedule;

import org.gluu.message.consumer.config.MessageConsumerProperties;
import org.gluu.message.consumer.repository.ILogCleaner;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.inject.Inject;
import java.util.Calendar;

/**
 * Created by eugeniuparvan on 10/31/16.
 */

public abstract class BaseScheduler<T extends PagingAndSortingRepository & ILogCleaner> {
    @Inject
    protected T repository;

    @Inject
    protected MessageConsumerProperties messageConsumerProperties;

    abstract int getDaysAfterYouCanDeleteLogs();

    public void deleteLogs() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -getDaysAfterYouCanDeleteLogs());
        repository.deleteAllByTimestampBefore(calendar.getTime());
    }
}
