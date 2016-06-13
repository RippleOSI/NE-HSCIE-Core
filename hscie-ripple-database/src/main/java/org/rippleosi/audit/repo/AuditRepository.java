/**
 * 
 */
package org.rippleosi.audit.repo;

import org.rippleosi.audit.model.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author WeatherillW
 *
 */
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

}
