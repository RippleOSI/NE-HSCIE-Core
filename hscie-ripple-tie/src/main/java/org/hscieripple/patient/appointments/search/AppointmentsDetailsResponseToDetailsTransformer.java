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

package org.hscieripple.patient.appointments.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.appointments.AppointmentsDetailsResponse;
import org.hscieripple.patient.appointments.model.HSCIEAppointmentDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class AppointmentsDetailsResponseToDetailsTransformer implements Transformer<AppointmentsDetailsResponse, HSCIEAppointmentDetails> {

    @Override
    public HSCIEAppointmentDetails transform(AppointmentsDetailsResponse response) {
    	 
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(), response.getDataSourceName());
		Date dateOfAppointment = HSCIEDateFormatter.toDate(response.getDateOfAppointment(), response.getDataSourceName());
		Date timeOfAppointment = HSCIEDateFormatter.toDate(response.getTimeOfAppointment(), response.getDataSourceName());
    	    	
		HSCIEAppointmentDetails details = new HSCIEAppointmentDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
            
			
        details.setAuthor(response.getAuthor());
		details.setDateCreated(dateCreated);
        details.setServiceTeam(response.getServiceTeam());
		details.setDateOfAppointment(dateOfAppointment);
		details.setTimeOfAppointment(timeOfAppointment);
		
        details.setLocation(response.getLocation());
		details.setStatus(response.getStatus());

        return details;
    }
}

