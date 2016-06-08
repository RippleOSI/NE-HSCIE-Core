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

package org.hscieripple.patient.appointments.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.appointments.AppointmentsDetailsResponse;
import org.hscieripple.patient.appointments.AppointmentsSummaryResponse;
import org.hscieripple.patient.appointments.AppointmentServiceSoap;
import org.hscieripple.patient.appointments.PairOfAppointmentsListKeyAppointmentsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.appointments.model.HSCIEAppointmentDetails;
import org.hscieripple.patient.appointments.model.HSCIEAppointmentSummary;
import org.hscieripple.patient.appointments.search.HSCIEAppointmentSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEAppointmentsSearch extends AbstractHSCIEService implements HSCIEAppointmentSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEAppointmentsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private AppointmentServiceSoap appointmentsService;

    @Override
    public List<HSCIEAppointmentSummary> findAllAppointments(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEAppointmentSummary> appointments = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEAppointmentSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            appointments.addAll(results);
        }

        return appointments;
    }

    @Override
    public HSCIEAppointmentDetails findAppointment(String patientId, String appointmentId, String source) {
        AppointmentsDetailsResponse response = new AppointmentsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = appointmentsService.findAppointmentsDetailsBO(nhsNumber, appointmentId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEAppointmentDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new AppointmentsDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEAppointmentSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfAppointmentsListKeyAppointmentsSummaryResultRow> results = new ArrayList<>();

        try {
            AppointmentsSummaryResponse response = appointmentsService.findAppointmentsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getAppointmentsList().getAppointmentsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new AppointmentsResponseToAppointmentsSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(AppointmentsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(AppointmentsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
