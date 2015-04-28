package cn.com.sinosoft.platform.ircs.repository;

import java.util.List;

import org.joda.time.LocalDateTime;

import cn.com.sinosoft.platform.ircs.domain.PersistentAuditEvent;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository /* extends JpaRepository<PersistentAuditEvent, String> */{

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

    List<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
