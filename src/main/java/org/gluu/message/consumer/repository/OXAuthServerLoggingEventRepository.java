package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by eugeniuparvan on 10/30/16.
 */
@RepositoryRestResource(collectionResourceRel = "oxauth-server-logs", path = "oxauth-server-logs")
@Transactional
public interface OXAuthServerLoggingEventRepository extends PagingAndSortingRepository<OXAuthServerLoggingEvent, Long>, ILogCleaner {

    @Query("SELECT log FROM OXAuthServerLoggingEvent log WHERE (:level IS NULL OR log.level = :level) " +
            "AND (:loggerName IS NULL OR log.loggerName = :loggerName) " +
            "AND (:formattedMessage IS NULL OR LOWER(log.formattedMessage) LIKE CONCAT ('%', LOWER(CAST(:formattedMessage as string)), '%')) " +
            "AND (CAST(:fromDate as date) IS NULL OR log.timestamp >= :fromDate) " +
            "AND (CAST(:toDate as date) IS NULL OR log.timestamp <= :toDate) ")
    Page<OXAuthServerLoggingEvent> query(@Param("level") String level, @Param("loggerName") String loggerName,
                                         @Param("formattedMessage") String formattedMessage,
                                         @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date fromDate,
                                         @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date toDate, Pageable pageable);

    @Override
    @RestResource(exported = false)
    OXAuthServerLoggingEvent save(OXAuthServerLoggingEvent s);

    @Override
    @RestResource(exported = false)
    void delete(OXAuthServerLoggingEvent t);
}
