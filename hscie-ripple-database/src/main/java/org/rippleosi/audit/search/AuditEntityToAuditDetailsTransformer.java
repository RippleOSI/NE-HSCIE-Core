/**
 * 
 */
package org.rippleosi.audit.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.audit.model.AuditDetails;
import org.rippleosi.audit.model.AuditEntity;

/**
 * @author WeatherillW
 *
 */
public class AuditEntityToAuditDetailsTransformer implements Transformer<AuditEntity, AuditDetails> {

	@Override
	public AuditDetails transform(AuditEntity auditEntity) {
		AuditDetails auditDetails = null;
		
		if(auditEntity != null) {
			auditDetails = new AuditDetails();
			
			auditDetails.setId(auditEntity.getId());
			auditDetails.setRequesterUsername(auditEntity.getRequesterUsername());
			auditDetails.setTargetNhsNumber(Long.parseLong(auditEntity.getTargetNhsNumber()));
			auditDetails.setRequestDateTime(auditEntity.getRequestDateTime());
			auditDetails.setTargetResource(auditEntity.getTargetResource());
		}
		
		return auditDetails;
	}

}
