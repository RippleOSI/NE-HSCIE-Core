/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.summary.model;

import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.patient.summary.model.PatientSummary;

/**
 */
public class HSCIEPatientSummary extends PatientSummary{

    private RepoSourceType source;
    private Boolean consentStatus;

    public RepoSourceType getSource() {
        return source;
    }

    public void setSource(RepoSourceType source) {
        this.source = source;
    }
    
    public Boolean isConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(Boolean value) {
        this.consentStatus = value;
    }

}