package org.gluu.message.consumer.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by eugeniuparvan on 10/31/16.
 */

public interface ILogCleaner {
    @Transactional
    void deleteAllByTimestampBefore(Date date);
}
