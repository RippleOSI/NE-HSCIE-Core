/*
 * Copyright 2015 Ripple OSI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hscieripple.patient.alerts.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HSCIEAlertDetails implements Serializable {

	 private String source;
	    private String sourceId;
	    private String alertType;
	    private String alertNote;
	    private String author;
	    private Date dateTime;

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

	    public String getAlertType() {
	        return alertType;
	    }

	    public void setAlertType(String alertType) {
	        this.alertType = alertType;
	    }

	    public String getAlertNote() {
	        return alertNote;
	    }

	    public void setAlertNote(String alertNote) {
	        this.alertNote = alertNote;
	    }

	    public String getAuthor() {
	        return author;
	    }

	    public void setAuthor(String author) {
	        this.author = author;
	    }

	    public Date getDateTime() {
	        return dateTime;
	    }

	    public void setDateTime(Date dateTime) {
	        this.dateTime = dateTime;
	    }
}
