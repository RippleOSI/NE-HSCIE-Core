/*
 *   Copyright 2016 Ripple OSI
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package org.rippleosi.audit.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditedAction;
import org.rippleosi.audit.model.AuditEntity;

/**
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
			auditDetails.setAction(AuditedAction.valueOf(auditEntity.getRequesterAction()));
		}
		
		return auditDetails;
	}

}
