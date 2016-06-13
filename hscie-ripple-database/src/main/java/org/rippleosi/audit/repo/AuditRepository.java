/**
 * 
 */
package org.rippleosi.audit.repo;

import org.rippleosi.audit.model.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author WeatherillW
 *
 */
public interface AuditRepository extends JpaRepository<AuditEntity, Long>, QueryDslPredicateExecutor<AuditEntity> {

}
