/**
 * 
 */
package org.rippleosi.audit.store;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditedAction;
import org.rippleosi.audit.model.AuditEntity;

/**
 * Package-access class that is used to transform from the core representation of an Audit record
 * {@link AuditDetails} to the persistence model representation {@link AuditEntity}
 * 
 * @author WeatherillW
 *
 */
class AuditDetailsToAuditEntityTransformer implements Transformer<AuditDetails, AuditEntity> {

	/**
	 * Note that if the auditDetails param in null this method will return null
	 * 
	 * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
	 * 
	 * @throws IllegalStateException - if any of the properties of the given {@link AuditDetails} param are not valid eg null
	 */
	@Override
	public AuditEntity transform(AuditDetails auditDetails) throws IllegalStateException {
		AuditEntity auditEntity = null;

		if (auditDetails != null) {
			auditEntity = new AuditEntity();

			auditEntity.setRequestDateTime(getRequestDateTime(auditDetails));
			auditEntity.setRequesterUsername(getRequesterUsername(auditDetails));
			auditEntity.setTargetNhsNumber(getTargetNhsNumber(auditDetails));
			auditEntity.setTargetResource(getTargetResource(auditDetails));
			auditEntity.setRequesterAction(getAction(auditDetails));
		}

		return auditEntity;
	}
	
	private Date getRequestDateTime(AuditDetails auditDetails) {
		Date requestDateTime = auditDetails.getRequestDateTime();
		
		if(requestDateTime == null) {
			throw new IllegalStateException(String.format("The Request datetime property is null on the given AuditDetails instance - %s", auditDetails));
		}
		
		return requestDateTime;
	}
	
	private String getRequesterUsername(AuditDetails auditDetails) {
		String requestorUsername = auditDetails.getRequesterUsername();
		
		if(requestorUsername == null) {
			throw new IllegalStateException(String.format("The Requester's username property is null on the given AuditDetails instance - %s", auditDetails));
		}
		
		return requestorUsername;
	}
	
	private String getTargetNhsNumber(AuditDetails auditDetails) {
		String targetNhsNumber = null;
		
		Long targetNhsNumberRaw = auditDetails.getTargetNhsNumber();
		
		targetNhsNumber = targetNhsNumberRaw.toString();
		
		if(targetNhsNumber.length() != 10) {
			throw new IllegalStateException(String.format("The target NHS property (%d) is not a valid NHS number on the given AuditDetails instance - %s", targetNhsNumberRaw, auditDetails));
		}
		
		return targetNhsNumber;
	}
	
	private String getTargetResource(AuditDetails auditDetails) {
		String targetResource = auditDetails.getTargetResource();
		
		if(targetResource == null) {
			throw new IllegalStateException(String.format("The requested resource is null on the given AuditDetails instance - %s", auditDetails));
		}
		
		return targetResource;
	}
	
	private String getAction(AuditDetails auditDetails) {
		AuditedAction action = auditDetails.getAction();
		
		String actionString = null;
		
		if(action != null) {
			actionString = action.name();
		
		} else {
			actionString = AuditedAction.NULL.name();
		}
		
		return actionString;
	}
}
