/**
 * 
 */
package org.rippleosi.audit.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.audit.model.AuditSummary;
import org.rippleosi.audit.model.AuditEntity;

/**
 * @author WeatherillW
 *
 */
public class AuditEntityToAuditSummaryTransformer implements Transformer<AuditEntity, AuditSummary> {

	@Override
	public AuditSummary transform(AuditEntity auditEntity) {
		AuditSummary auditSummary = null;
		
		if(auditEntity != null) {
			auditSummary = new AuditSummary();
			
			auditSummary.setId(auditEntity.getId());
			auditSummary.setRequesterUsername(auditEntity.getRequesterUsername());
			auditSummary.setTargetNhsNumber(Long.parseLong(auditEntity.getTargetNhsNumber()));
			auditSummary.setRequestDateTime(auditEntity.getRequestDateTime());
		}
		
		return auditSummary;
	}

}
