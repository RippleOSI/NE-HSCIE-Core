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
package org.hscieripple.audit.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class AuditSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String requesterUsername;
	private long targetNhsNumber;
	private Date requestDateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
		return "AuditSummary [id=" + id + ", "
				+ (requesterUsername != null ? "requesterUsername=" + requesterUsername + ", " : "")
				+ "targetNhsNumber=" + targetNhsNumber + ", "
				+ (requestDateTime != null ? "requestDateTime=" + requestDateTime : "") + "]";
	}


}
