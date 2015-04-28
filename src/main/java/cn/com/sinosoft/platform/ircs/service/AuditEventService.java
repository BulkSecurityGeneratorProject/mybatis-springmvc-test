package cn.com.sinosoft.platform.ircs.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.platform.ircs.config.audit.AuditEventConverter;
import cn.com.sinosoft.platform.ircs.domain.PersistentAuditEvent;
import cn.com.sinosoft.platform.ircs.repository.PersistenceAuditEventRepository;

/**
 * Service for managing audit events.
 * <p/>
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    //@Inject
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Inject
    private AuditEventConverter auditEventConverter;

    public List<AuditEvent> findAll() {
        return null;//FIXME: auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
    }

    public List<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        List<PersistentAuditEvent> persistentAuditEvents =
            persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }
}
