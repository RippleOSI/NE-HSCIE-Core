/**
 * 
 */
package org.hscieripple.audit.search;

import java.util.List;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditSummary;
import org.rippleosi.common.repo.Repository;
import org.rippleosi.search.common.model.PageableTableQuery;

/**
 * @author WeatherillW
 *
 */
public interface AuditSearch extends Repository {
	
	public List<AuditSummary>findAllAudits(PageableTableQuery tableQuery);
		
	public List<AuditDetails> findAuditsByPatientAndUser(long patientNhsNumber, String requesterUsername, PageableTableQuery tableQuery);
	
//	public Set<AuditDetails> findAuditsByPatient(long patientNhsNumber);
//	
//	public Set<AuditDetails> findAuditsByUser(String requesterUsername);
}
