package cn.com.sinosoft.platform.ircs.repository;

import cn.com.sinosoft.platform.ircs.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
