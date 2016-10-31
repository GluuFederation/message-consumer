package org.gluu.message.consumer.repository;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by eugeniuparvan on 10/31/16.
 */

public interface ILogCleaner {
    @RestResource(exported = false)
    @Transactional
    void deleteAllByTimestampBefore(Date date);
}
