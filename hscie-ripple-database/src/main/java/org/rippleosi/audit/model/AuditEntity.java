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
package org.rippleosi.audit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity
@Table(name = "audit")
public class AuditEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "requester_username")
	private String requesterUsername;
	
	@Column(name = "target_nhs_number")
	private String targetNhsNumber;
	
	@Column(name = "target_resource")
	private String targetResource;
	
	@Column(name = "request_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDateTime;
	
	@Column(name = "requester_action")
	private String requesterAction;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRequesterUsername() {
		return requesterUsername;
	}
	
	public void setRequesterUsername(String requesterUsername) {
		this.requesterUsername = requesterUsername;
	}
	
	public String getTargetNhsNumber() {
		return targetNhsNumber;
	}
	
	public void setTargetNhsNumber(String targetNhsNumber) {
		this.targetNhsNumber = targetNhsNumber;
	}
	
	public String getTargetResource() {
		return targetResource;
	}
	
	public void setTargetResource(String targetResource) {
		this.targetResource = targetResource;
	}
	
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public String getRequesterAction() {
		return requesterAction;
	}

	public void setRequesterAction(String requesterAction) {
		this.requesterAction = requesterAction;
	}
}
