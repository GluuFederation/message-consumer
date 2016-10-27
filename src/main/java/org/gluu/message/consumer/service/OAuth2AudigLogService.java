package org.gluu.message.consumer.service;

import org.gluu.message.consumer.domain.OAuth2AuditLog;
import org.gluu.message.consumer.repository.OAuth2AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Service
@Transactional
public class OAuth2AudigLogService {

    @Inject
    private OAuth2AuditLogRepository oAuth2AuditLogRepository;

    public Long add(OAuth2AuditLog oAuth2AuditLog) {
        oAuth2AuditLogRepository.save(oAuth2AuditLog);
        return oAuth2AuditLog.getId();
    }
}
