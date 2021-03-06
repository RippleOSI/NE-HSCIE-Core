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

import static org.junit.Assert.*;

import java.util.Date;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditSummary;
import org.junit.Before;
import org.junit.Test;
import org.rippleosi.audit.model.AuditEntity;

public class AuditEntityToAuditSummaryTransformerTest {

	AuditEntity auditEntity;
	AuditEntityToAuditSummaryTransformer transformer;
	
	@Before
	public void setUp() throws Exception {
		transformer = new AuditEntityToAuditSummaryTransformer();
		
		auditEntity = new AuditEntity();
		auditEntity.setId(1l);
		auditEntity.setRequestDateTime(new Date());
		auditEntity.setRequesterUsername("bob");
		auditEntity.setTargetNhsNumber("1234567890");
		auditEntity.setTargetResource("/resouce");
		auditEntity.setRequesterAction("CREATE");
	}

	@Test
	public void testTransform() {
		AuditSummary auditSummary = transformer.transform(auditEntity);
		
		assertNotNull("Make sure the audit details is not null", auditEntity);
		assertEquals("Make sure that the requester usernames match", auditSummary.getRequesterUsername(), auditEntity.getRequesterUsername());
		assertEquals("Make sure that the target NHS numbers match", auditSummary.getTargetNhsNumber(), Long.parseLong((auditEntity.getTargetNhsNumber())));
		assertEquals("Make sure that the request datetimes match", auditSummary.getRequestDateTime(), auditEntity.getRequestDateTime());
		assertEquals("Make sure that the actions match", auditSummary.getAction().name(), auditEntity.getRequesterAction());
	}

}
