/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the ReferralSTFT License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.ReferralSTFT.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.referrals.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HSCIEReferralDetails implements Serializable {

    private String source;
    private String sourceId;
    private String author;
    private Date dateCreated;
    private String serviceTeam;
    private Date dateOfReferral;
    private Date timeOfReferral;
    private String location;
    private String status;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getServiceTeam() {
        return serviceTeam;
    }

    public void setServiceTeam(String serviceTeam) {
        this.serviceTeam = serviceTeam;
    }

    public Date getDateOfReferral() {
        return dateOfReferral;
    }

    public void setDateOfReferral(Date dateOfReferral) {
        this.dateOfReferral = dateOfReferral;
    }

    public Date getTimeOfReferral() {
        return timeOfReferral;
    }

    public void setTimeOfReferral(Date timeOfReferral) {
        this.timeOfReferral = timeOfReferral;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
