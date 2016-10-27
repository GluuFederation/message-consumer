package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.OAuth2AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@RepositoryRestResource(collectionResourceRel = "oauth2-audit-logs", path = "oauth2-audit-logs")
public interface OAuth2AuditLogRepository extends PagingAndSortingRepository<OAuth2AuditLog, Long> {

    @Query("SELECT log FROM OAuth2AuditLog log WHERE (:ip IS NULL OR log.ip = :ip) " +
            "AND (:clientId IS NULL OR log.clientId = :clientId) " +
            "AND (:username IS NULL OR log.username = :username) " +
            "AND (:scope IS NULL OR log.scope like '%'||:scope||'%' ) " +
            "AND (:success IS NULL OR log.success = :success) " +
            "AND (cast(:fromDate as date) IS NULL OR log.timestamp >= :fromDate) " +
            "AND (cast(:toDate as date) IS NULL OR log.timestamp <= :toDate) ")
    Page<OAuth2AuditLog> query(@Param("ip") String ip, @Param("clientId") String clientId,
                               @Param("username") String username, @Param("scope") String scope,
                               @Param("success") Boolean success, @Param("fromDate") @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss.SSS") Date fromDate,
                               @Param("toDate") @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss.SSS") Date toDate, Pageable pageable);

    @Override
    @RestResource(exported = false)
    OAuth2AuditLog save(OAuth2AuditLog s);

    @Override
    @RestResource(exported = false)
    void delete(OAuth2AuditLog t);
}
