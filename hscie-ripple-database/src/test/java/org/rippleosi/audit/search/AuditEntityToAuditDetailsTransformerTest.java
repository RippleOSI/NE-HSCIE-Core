package org.rippleosi.audit.search;

import static org.junit.Assert.*;

import java.util.Date;

import org.hscieripple.audit.model.AuditDetails;
import org.junit.Before;
import org.junit.Test;
import org.rippleosi.audit.model.AuditEntity;

public class AuditEntityToAuditDetailsTransformerTest {

	AuditEntity auditEntity;
	AuditEntityToAuditDetailsTransformer transformer;
	
	@Before
	public void setUp() throws Exception {
		transformer = new AuditEntityToAuditDetailsTransformer();
		
		auditEntity = new AuditEntity();
		auditEntity.setId(1l);
		auditEntity.setRequestDateTime(new Date());
		auditEntity.setRequesterUsername("bob");
		auditEntity.setTargetNhsNumber("1234567890");
		auditEntity.setTargetResource("/resouce");
	}

	@Test
	public void testTransform() {
		AuditDetails auditDetails = transformer.transform(auditEntity);
		
		assertNotNull("Make sure the audit details is not null", auditEntity);
		assertEquals("Make sure that the requester usernames match", auditDetails.getRequesterUsername(), auditEntity.getRequesterUsername());
		assertEquals("Make sure that the target NHS numbers match", auditDetails.getTargetNhsNumber(), Long.parseLong((auditEntity.getTargetNhsNumber())));
		assertEquals("Make sure that the request datetimes match", auditDetails.getRequestDateTime(), auditEntity.getRequestDateTime());
		assertEquals("Make sure that the target resources match", auditDetails.getTargetResource(), auditEntity.getTargetResource());
	}

}
