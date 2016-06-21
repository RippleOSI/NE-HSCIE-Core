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
package org.hscieripple.patient.keyworkers.model;

import java.util.Date;

public class KeyWorkerSummary {

    private String source;
    private String sourceId;
    private String name;
    private String role;
    private String contactNumber;
    private Date keyContactDate;
    private Date keyContactTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public Date getKeyContactDate() {
        return keyContactDate;
    }

    public void setKeyContactDate(Date keyContactDate) {
        this.keyContactDate = keyContactDate;
    }
    
    public Date getKeyContactTime() {
        return keyContactTime;
    }

    public void setKeyContactTime(Date keyContactTime) {
        this.keyContactTime = keyContactTime;
    }
}
