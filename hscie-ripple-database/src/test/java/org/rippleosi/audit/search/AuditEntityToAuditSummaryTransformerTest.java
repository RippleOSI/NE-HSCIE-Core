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
		auditEntity.setRequestDateTime(new Date());
		auditEntity.setRequesterUsername("bob");
		auditEntity.setTargetNhsNumber("1234567890");
		auditEntity.setTargetResource("/resouce");
	}

	@Test
	public void testTransform() {
		AuditSummary auditSummary = transformer.transform(auditEntity);
		
		assertNotNull("Make sure the audit details is not null", auditEntity);
		assertEquals("Make sure that the requester usernames match", auditSummary.getRequesterUsername(), auditEntity.getRequesterUsername());
		assertEquals("Make sure that the target NHS numbers match", auditSummary.getTargetNhsNumber(), Long.parseLong((auditEntity.getTargetNhsNumber())));
		assertEquals("Make sure that the request datetimes match", auditSummary.getRequestDateTime(), auditEntity.getRequestDateTime());
	}

}
