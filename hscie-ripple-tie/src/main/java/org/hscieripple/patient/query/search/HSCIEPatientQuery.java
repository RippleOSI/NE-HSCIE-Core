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

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.query.PairOfResultsSetKeyResultRow;
import org.hscieripple.patient.query.PatientDetailsResponse;
import org.hscieripple.patient.query.PatientServiceSoap;
import org.rippleosi.common.exception.ConfigurationException;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.rippleosi.common.util.DateFormatter;
import org.rippleosi.patient.summary.model.PatientDetails;
import org.rippleosi.patient.summary.model.PatientQueryParams;
import org.rippleosi.patient.summary.model.PatientSummary;
import org.rippleosi.patient.summary.search.PatientSearch;
import org.rippleosi.search.patient.stats.model.PatientTableQuery;
import org.rippleosi.search.reports.table.model.ReportTableQuery;
import org.rippleosi.search.setting.table.model.SettingTableQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEPatientQuery extends AbstractHSCIEService implements PatientSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEPatientQuery.class);

    @Autowired
    private PatientServiceSoap patientService;

    @Override
    public List<PatientSummary> findPatientsByQueryObject(PatientQueryParams params) {
        List<PairOfResultsSetKeyResultRow> patients = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(params.getNhsNumber());
        Date dateOfBirth = params.getDateOfBirth();

        try {
            PatientDetailsResponse response = patientService.findPatientBO(nhsNumber,
                                                                           params.getForename(),
                                                                           params.getSurname(),
                                                                           params.getGender(),
                                                                           DateFormatter.toSimpleDateString(dateOfBirth));

            if (isSuccessfulResponse(response)) {
                patients = response.getResultsSet().getResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage());
        }

        return CollectionUtils.collect(patients, new PatientResponseToPatientSummaryTransformer(), new ArrayList<>());
    }

    @Override
    public List<PatientSummary> findAllPatients() {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public List<PatientSummary> findAllMatchingPatients(List<String> nhsNumbers, ReportTableQuery tableQuery) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public PatientDetails findPatient(String patientId) {
        List<PairOfResultsSetKeyResultRow> patients = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            PatientDetailsResponse response = patientService.findPatientBO(nhsNumber, null, null, null, null);

            if (isSuccessfulResponse(response)) {
                patients = response.getResultsSet().getResultRow();
            }

            return new PatientResponseToPatientDetailsTransformer().transform(patients.get(0));
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage());

            return new PatientDetails();
        }
    }

    @Override
    public List<PatientSummary> findPatientsBySearchString(PatientTableQuery tableQuery) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public Long countPatientsBySearchString(PatientTableQuery tableQuery) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public PatientSummary findPatientSummary(String patientId) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public List<PatientSummary> findAllPatientsByDepartment(SettingTableQuery tableQuery) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    @Override
    public Long findPatientCountByDepartment(String department) {
        throw ConfigurationException.unimplementedTransaction(PatientSearch.class);
    }

    private boolean isSuccessfulResponse(PatientDetailsResponse response) {
        return response.getStatusCode().equalsIgnoreCase("OK");
    }
}
