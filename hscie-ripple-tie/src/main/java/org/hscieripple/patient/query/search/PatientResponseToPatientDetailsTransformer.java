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
package org.hscieripple.patient.query.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.contacts.search.HSCIEContactSearch;
import org.hscieripple.patient.contacts.search.HSCIEContactSearchFactory;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourceResponseToSummaryTransformer;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.details.model.HSCIEPatientDetails;
import org.hscieripple.patient.medications.search.HSCIEMedicationSearch;
import org.hscieripple.patient.medications.search.HSCIEMedicationSearchFactory;
import org.hscieripple.patient.problems.search.HSCIEProblemSearch;
import org.hscieripple.patient.problems.search.HSCIEProblemSearchFactory;
import org.hscieripple.patient.query.ResultRow;
import org.hscieripple.patient.transfers.search.HSCIETransferOfCareSearch;
import org.hscieripple.patient.transfers.search.HSCIETransferOfCareSearchFactory;
import org.rippleosi.common.exception.DataNotFoundException;
import org.rippleosi.common.util.DateFormatter;
import org.rippleosi.patient.contacts.model.ContactHeadline;
import org.rippleosi.patient.medication.model.MedicationHeadline;
import org.rippleosi.patient.problems.model.ProblemHeadline;
import org.rippleosi.patient.summary.model.PatientHeadline;
import org.rippleosi.patient.summary.model.TransferHeadline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientResponseToPatientDetailsTransformer implements Transformer<ResultRow, HSCIEPatientDetails> {

    @Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEContactSearchFactory contactSearchFactory;
    
    @Autowired
    private HSCIEProblemSearchFactory problemSearchFactory;
    
    @Autowired
    private HSCIETransferOfCareSearchFactory transferSearchFactory;


    @Override
    public HSCIEPatientDetails transform(final ResultRow response) {

        final String sourceId = String.valueOf(response.getPersonNumber());
        final String name = response.getForename() + " " + response.getSurname();
        final String address = response.getAddress() + ", " + response.getPostCode();
        final Date dateOfBirth = DateFormatter.toDate(response.getDOB());
        final String nhsNumber = String.valueOf(response.getNHSNumber());

        final HSCIEPatientDetails details = new HSCIEPatientDetails();

        details.setId(sourceId);
        details.setName(name);
        details.setAddress(address);
        details.setDateOfBirth(dateOfBirth);
        details.setGender(response.getGender());
        details.setNhsNumber(nhsNumber);
        details.setPasNumber(response.getPersonNumber());
        details.setOptIn(response.isConsentStatus());

        details.setContacts(findContacts(nhsNumber));
        details.setTransfers(findTransfers(nhsNumber));

        return details;
    }

    private List<PatientHeadline> findContacts(final String patientId) {
        try {
            final List<DataSourceSummary> dataSources = findDataSources(patientId, "contacts");

            final HSCIEContactSearch contactSearch = contactSearchFactory.select(null);

            final List<ContactHeadline> contacts = contactSearch.findAllContactHeadlines(patientId, dataSources);
            
            return CollectionUtils.collect(contacts, new ContactHeadlineToPatientHeadlineTransformer(), new ArrayList<>());
        }
        catch (DataNotFoundException ignore) {
            return Collections.emptyList();
        }
    }
    
    
    private List<PatientHeadline> findProblems(final String patientId) {
        try {
            final List<DataSourceSummary> dataSources = findDataSources(patientId, "transfers");

            final HSCIEProblemSearch problemSearch = problemSearchFactory.select(null);

            final List<ProblemHeadline> problems = problemSearch.findAllProblemHeadlines(patientId, dataSources);
            
            return CollectionUtils.collect(problems, new ProblemHeadlineToPatientHeadlineTransformer(), new ArrayList<>());
        }
        catch (DataNotFoundException ignore) {
            return Collections.emptyList();
        }
    }
    
    
    
    private List<TransferHeadline> findTransfers(final String patientId) {
        try {
            final List<DataSourceSummary> dataSources = findDataSources(patientId, "transfers");

            final HSCIETransferOfCareSearch transferSearch = transferSearchFactory.select(null);

            final List<TransferHeadline> transfers = transferSearch.findAllTransferOfCareHeadlines(patientId, dataSources);
            
            return CollectionUtils.collect(transfers, new TransferOfCareHeadlineToPatientHeadlineTransformer(), new ArrayList<>());
        }
        catch (DataNotFoundException ignore) {
            return Collections.emptyList();
        }
    }

    private List<DataSourceSummary> findDataSources(final String patientId, final String dataType) {
        try {
            final DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);

            return dataSourcesSearch.findAvailableDataSources(patientId, dataType);
        }
        catch (DataNotFoundException ignore) {
            return Collections.emptyList();
        }
    }
}