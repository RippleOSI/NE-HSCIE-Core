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

package org.hscieripple.patient.alerts.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.alerts.AlertsDetailsResponse;
import org.hscieripple.patient.alerts.AlertsSummaryResponse;
import org.hscieripple.patient.alerts.AlertServiceSoap;
import org.hscieripple.patient.alerts.PairOfAlertsListKeyAlertsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.alerts.model.HSCIEAlertDetails;
import org.hscieripple.patient.alerts.model.HSCIEAlertSummary;
import org.hscieripple.patient.alerts.search.HSCIEAlertSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEAlertsSearch extends AbstractHSCIEService implements HSCIEAlertSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEAlertsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private AlertServiceSoap alertsService;

    @Override
    public List<HSCIEAlertSummary> findAllAlerts(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEAlertSummary> alerts = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEAlertSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            alerts.addAll(results);
        }

        return alerts;
    }

    @Override
    public HSCIEAlertDetails findAlert(String patientId, String alertId, String source) {
        AlertsDetailsResponse response = new AlertsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = alertsService.findAlertsDetailsBO(nhsNumber, alertId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEAlertDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new AlertsDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEAlertSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfAlertsListKeyAlertsSummaryResultRow> results = new ArrayList<>();

        try {
            AlertsSummaryResponse response = alertsService.findAlertsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getAlertsList().getAlertsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new AlertsResponseToAlertsSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(AlertsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(AlertsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
