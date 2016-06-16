/**
 * 
 */
package org.hscieripple.audit.search;

import java.util.List;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditSummary;
import org.rippleosi.common.repo.Repository;

/**
 * @author WeatherillW
 *
 */
public interface AuditSearch extends Repository {
	
	public List<AuditSummary> findAllAudits(int page);

	public List<AuditSummary> findAllAuditsByUsername(int page, String username);

	public List<AuditSummary> findAllAuditsByPatientId(int page, String patientId);

	public AuditDetails findAudit(long auditId);

	public long countAuditsByUsername(String username);

	public long countAuditsByPatientId(String patientId);

	public long countAudits();
}
