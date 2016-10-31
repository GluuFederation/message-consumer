package org.gluu.message.consumer.schedule;

import org.gluu.message.consumer.repository.OXAuthServerLoggingEventRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by eugeniuparvan on 10/31/16.
 */
@Component
public class OXAuthServerScheduler {

    @Inject
    private OXAuthServerLoggingEventRepository repository;

}
