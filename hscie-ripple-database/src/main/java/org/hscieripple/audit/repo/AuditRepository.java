/**
 * 
 */
package org.hscieripple.audit.repo;

import org.hscieripple.audit.model.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author WeatherillW
 *
 */
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

}
