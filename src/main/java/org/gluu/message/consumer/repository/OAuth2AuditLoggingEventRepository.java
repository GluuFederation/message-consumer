package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.OAuth2AuditLoggingEvent;
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
 * Created by eugeniuparvan on 10/26/16.
 */
@RepositoryRestResource(collectionResourceRel = "oauth2-audit-logs", path = "oauth2-audit-logs")
@Transactional
public interface OAuth2AuditLoggingEventRepository extends PagingAndSortingRepository<OAuth2AuditLoggingEvent, Long>, ILogCleaner {

    @Query("SELECT log FROM OAuth2AuditLoggingEvent log WHERE (:ip IS NULL OR log.ip = :ip) " +
            "AND (:clientId IS NULL OR log.clientId = :clientId) " +
            "AND (:action IS NULL OR log.action = :action) " +
            "AND (:username IS NULL OR log.username = :username) " +
            "AND (cast(:scope as text) IS NULL OR log.scope LIKE CONCAT ('%', cast(:scope as text), '%')) " +
            "AND (:success IS NULL OR log.success = :success) " +
            "AND (cast(:fromDate as date) IS NULL OR log.timestamp >= :fromDate) " +
            "AND (cast(:toDate as date) IS NULL OR log.timestamp <= :toDate) ")
    Page<OAuth2AuditLoggingEvent> query(@Param("ip") String ip, @Param("clientId") String clientId,
                                        @Param("action") String action, @Param("username") String username,
                                        @Param("scope") String scope, @Param("success") Boolean success,
                                        @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date fromDate,
                                        @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date toDate, Pageable pageable);

    @Override
    @RestResource(exported = false)
    OAuth2AuditLoggingEvent save(OAuth2AuditLoggingEvent s);

    @Override
    @RestResource(exported = false)
    void delete(OAuth2AuditLoggingEvent t);
}
