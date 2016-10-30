package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEvent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eugeniuparvan on 10/30/16.
 */
@RepositoryRestResource(collectionResourceRel = "oxauth-server-logs", path = "oxauth-server-logs")
@Transactional
public interface OXAuthServerLoggingEventRepository extends PagingAndSortingRepository<OXAuthServerLoggingEvent, Long> {

    @Override
    @RestResource(exported = false)
    OXAuthServerLoggingEvent save(OXAuthServerLoggingEvent s);

    @Override
    @RestResource(exported = false)
    void delete(OXAuthServerLoggingEvent t);
}
