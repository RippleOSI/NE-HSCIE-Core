/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the HSCIEAllergy License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.HSCIEAllergy.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.allergies.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HSCIEAllergyDetails implements Serializable {

    private String source;
    private String sourceId;
    private String cause;
    private String causeCode;
    private String reaction;
    private String causeTerminology;
    private String author;
    private Date dateCreated

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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }    
    
    public String getCauseCode() {
        return causeCode;
    }

    public void setCauseCode(String causeCode) {
        this.causeCode = causeCode;
    }    
    
    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }    
    
    public String getCauseTerminology() {
        return causeTerminology;
    }

    public void setCauseTerminology(String causeTerminology) {
        this.causeTerminology = causeTerminology;
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
}
