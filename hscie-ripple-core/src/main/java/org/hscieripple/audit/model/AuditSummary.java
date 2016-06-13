package org.hscieripple.audit.model;

import java.io.Serializable;
import java.util.Date;

public class AuditSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String requesterUsername;
	private long targetNhsNumber;
	private Date requestDateTime;
	
	public String getRequesterUsername() {
		return requesterUsername;
	}
	
	public void setRequesterUsername(String requesterUsername) {
		this.requesterUsername = requesterUsername;
	}
	
	public long getTargetNhsNumber() {
		return targetNhsNumber;
	}
	
	public void setTargetNhsNumber(long targetNhsNumber) {
		this.targetNhsNumber = targetNhsNumber;
	}
	
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	@Override
	public String toString() {
		return "AuditSummary [" + (requesterUsername != null ? "requesterUsername=" + requesterUsername + ", " : "")
				+ "targetNhsNumber=" + targetNhsNumber + ", "
				+ (requestDateTime != null ? "requestDateTime=" + requestDateTime : "") + "]";
	}
}
