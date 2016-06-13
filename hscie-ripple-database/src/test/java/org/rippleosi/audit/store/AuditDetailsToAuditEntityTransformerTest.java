package org.rippleosi.audit.store;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import org.hscieripple.audit.model.AuditDetails;
import org.junit.Before;
import org.junit.Test;
import org.rippleosi.audit.model.AuditEntity;

public class AuditDetailsToAuditEntityTransformerTest {

	private AuditDetailsToAuditEntityTransformer transformer;
	
	private AuditDetails nullRequesterUsernameAuditDetails;
	private AuditDetails nullTargetNhsNumberAuditDetails;
	private AuditDetails nullRequestDatetimeAuditDetails;
	private AuditDetails nullTargetResourceAuditDetails;
	private AuditDetails validAuditDetails;
	
	@Before
	public void setUp() throws Exception {
		transformer = new AuditDetailsToAuditEntityTransformer();
		
		nullRequesterUsernameAuditDetails = new AuditDetails();
		nullRequesterUsernameAuditDetails.setRequestDateTime(new Date());
		nullRequesterUsernameAuditDetails.setTargetNhsNumber(1234567890l);
		nullRequesterUsernameAuditDetails.setTargetResource("/resource");
		
		nullTargetNhsNumberAuditDetails = new AuditDetails();
		nullTargetNhsNumberAuditDetails.setRequestDateTime(new Date());
		nullTargetNhsNumberAuditDetails.setRequesterUsername("bob");
		nullTargetNhsNumberAuditDetails.setTargetResource("/resource");
		
		nullRequestDatetimeAuditDetails = new AuditDetails();
		nullRequestDatetimeAuditDetails.setTargetNhsNumber(1234567890l);
		nullRequestDatetimeAuditDetails.setRequesterUsername("bob");
		nullRequestDatetimeAuditDetails.setTargetResource("/resource");
		
		nullTargetResourceAuditDetails = new AuditDetails();
		nullTargetResourceAuditDetails.setTargetNhsNumber(1234567890l);
		nullTargetResourceAuditDetails.setRequesterUsername("bob");
		nullTargetResourceAuditDetails.setRequestDateTime(new Date());
		
		nullTargetResourceAuditDetails = new AuditDetails();
		nullTargetResourceAuditDetails.setTargetNhsNumber(1234567890l);
		nullTargetResourceAuditDetails.setRequesterUsername("bob");
		nullTargetResourceAuditDetails.setRequestDateTime(new Date());
	}

	@Test
	public void testNullAuditDetailsTransform() {
		assertNull("Make sure that the output is null", transformer.transform(null));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNullRequesterUsernameAuditDetailsTransform() {
		transformer.transform(nullRequesterUsernameAuditDetails);
		fail("An illegal state exception should have been thrown since the given audit details was missing a requester username value");
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNullTargetNhsNumberAuditDetailsTransform() {
		transformer.transform(nullTargetNhsNumberAuditDetails);
		fail("An illegal state exception should have been thrown since the given audit details was missing a target NHS number value");
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNullRequestDatetimeAuditDetailsTransform() {
		transformer.transform(nullRequestDatetimeAuditDetails);
		fail("An illegal state exception should have been thrown since the given audit details was missing a request datetime value");
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNullTargetResourceAuditDetailsTransform() {
		transformer.transform(nullTargetResourceAuditDetails);
		fail("An illegal state exception should have been thrown since the given audit details was missing a target resource value");
	}
	
	public void testAuditDetailsTransform() {
		AuditEntity auditEntity = transformer.transform(validAuditDetails);
		
		assertNotNull("Make sure the audit entity is not null", auditEntity);
		assertEquals("Makre sure that the requester usernames match", auditEntity.getRequesterUsername(), validAuditDetails.getRequesterUsername());
		assertEquals("Makre sure that the target NHS numbers match", auditEntity.getTargetNhsNumber(), Long.toString(validAuditDetails.getTargetNhsNumber()));
		assertEquals("Makre sure that the request datetimes match", auditEntity.getRequestDateTime(), validAuditDetails.getRequestDateTime());
		assertEquals("Makre sure that the target resources match", auditEntity.getTargetResource(), validAuditDetails.getTargetResource());
	}

}
