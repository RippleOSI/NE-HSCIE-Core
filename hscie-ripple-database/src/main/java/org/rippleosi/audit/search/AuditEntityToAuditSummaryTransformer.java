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
import org.hscieripple.audit.model.AuditSummary;
import org.hscieripple.audit.model.AuditedAction;
import org.rippleosi.audit.model.AuditEntity;

/**
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
			auditSummary.setAction(AuditedAction.valueOf(auditEntity.getRequesterAction()));
		}
		
		return auditSummary;
	}

}
