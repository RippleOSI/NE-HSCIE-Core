/**
 * 
 */
package org.hscieripple.audit.model;

import java.net.URL;
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
 * @author WeatherillW
 *
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
	private int targetNhsNumber;
	
	@Column(name = "target_resource")
	private URL targetResource;
	
	@Column(name = "request_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDateTime;
	
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
	
	public int getTargetNhsNumber() {
		return targetNhsNumber;
	}
	
	public void setTargetNhsNumber(int targetNhsNumber) {
		this.targetNhsNumber = targetNhsNumber;
	}
	
	public URL getTargetResource() {
		return targetResource;
	}
	
	public void setTargetResource(URL targetResource) {
		this.targetResource = targetResource;
	}
	
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
}
