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
package org.hscieripple.patient.medications.search;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.medications.MedicationsDetailsResponse;
import org.hscieripple.patient.medications.model.HSCIEMedicationDetails;
import org.rippleosi.common.util.HSCIEDateFormatter;

public class MedicationDetailsResponseToDetailsTransformer implements Transformer<MedicationsDetailsResponse, HSCIEMedicationDetails> {

    @Override
    public HSCIEMedicationDetails transform(MedicationsDetailsResponse response) {
    	 
    	Date startDate = HSCIEDateFormatter.toDate(response.getStartDate(),response.getDataSourceName());
    	Date startTime = HSCIEDateFormatter.toDate(response.getStartTime(),response.getDataSourceName());
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(),response.getDataSourceName());    	
    	
    	    	
    	HSCIEMedicationDetails details = new HSCIEMedicationDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
            
        details.setName(response.getName());
        details.setDoseAmount(response.getDoseAmount());
        details.setDoseDirections(response.getDoseDirections());
        details.setDoseTiming(response.getDoseTiming());
        details.setRoute(response.getRoute());
        details.setStartDate(startDate);
        details.setStartTime(startTime);
        details.setMedicationCode(response.getMedicationCode());
        details.setMedicationTerminology(response.getMedicationTerminology());
        details.setAuthor(response.getAuthor());
        details.setDateCreated(dateCreated);

        return details;
    }
}
