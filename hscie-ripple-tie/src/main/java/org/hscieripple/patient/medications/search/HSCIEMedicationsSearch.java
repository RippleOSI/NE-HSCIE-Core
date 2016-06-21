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

package org.hscieripple.patient.medications.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.medications.MedicationsDetailsResponse;
import org.hscieripple.patient.medications.MedicationsHeadlineResponse;
import org.hscieripple.patient.medications.MedicationsSummaryResponse;
import org.hscieripple.patient.medications.PairOfMedicationsListKeyMedicationsHeadlineResultRow;
import org.hscieripple.patient.medications.MedicationServiceSoap;
import org.hscieripple.patient.medications.PairOfMedicationsListKeyMedicationsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.medications.model.HSCIEMedicationDetails;
import org.hscieripple.patient.medications.model.HSCIEMedicationSummary;
import org.hscieripple.patient.medications.search.HSCIEMedicationSearch;
import org.rippleosi.patient.medication.model.MedicationHeadline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEMedicationsSearch extends AbstractHSCIEService implements HSCIEMedicationSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEMedicationsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private MedicationServiceSoap medicationsService;

    @Override
    public List<HSCIEMedicationSummary> findAllMedications(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEMedicationSummary> medications = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEMedicationSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            medications.addAll(results);
        }

        return medications;
    }

    @Override
    public HSCIEMedicationDetails findMedication(String patientId, String medicationId, String source) {
        MedicationsDetailsResponse response = new MedicationsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = medicationsService.findMedicationsDetailsBO(nhsNumber, medicationId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEMedicationDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new MedicationDetailsResponseToDetailsTransformer().transform(response);
    }
    
    
    
    
    @Override
    public List<MedicationHeadline> findAllMedicationHeadlines(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<MedicationHeadline> medications = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<MedicationHeadline> results = makeHeadlineCall(nhsNumber, summary.getSourceId());

            medications.addAll(results);
        }

        return medications;
    }
    
    
    

    private List<HSCIEMedicationSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfMedicationsListKeyMedicationsSummaryResultRow> results = new ArrayList<>();

        try {
            MedicationsSummaryResponse response = medicationsService.findMedicationsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getMedicationsList().getMedicationsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new MedicationResponseToMedicationSummaryTransformer(), new ArrayList<>());
    }
    
    
    
    private List<MedicationHeadline> makeHeadlineCall(Long nhsNumber, String source) {
        List<PairOfMedicationsListKeyMedicationsHeadlineResultRow> results = new ArrayList<>();

        try {
            MedicationsHeadlineResponse response = medicationsService.findMedicationsHeadlinesBO(nhsNumber, source);

            if (isSuccessfulHeadlineResponse(response)) {
                results = response.getMedicationsList().getMedicationsHeadlineResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new MedicationHeadlineResponseToHeadlineTransformer(), new ArrayList<>());
    }
    
    
    
    

    private boolean isSuccessfulSummaryResponse(MedicationsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
    
    private boolean isSuccessfulHeadlineResponse(MedicationsHeadlineResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(MedicationsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
