package org.hscieripple.patient.alerts.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.alerts.AlertServiceSoap;
import org.hscieripple.patient.alerts.AlertsDetailsResponse;
import org.hscieripple.patient.alerts.AlertsSummaryResponse;
import org.hscieripple.patient.alerts.PairOfAlertsListKeyAlertsSummaryResultRow;
import org.hscieripple.patient.alerts.model.AlertDetails;
import org.hscieripple.patient.alerts.model.AlertSummary;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.keyworker.search.HSCIEKeyWorkerSearch;
import org.hscieripple.patient.keyworker.search.KeyWorkerDetailsResponseToDetailsTransformer;
import org.hscieripple.patient.keyworker.search.KeyWorkerResponseToKeyWorkerSummaryTransformer;
import org.hscieripple.patient.keyworkers.KWDetailsResponse;
import org.hscieripple.patient.keyworkers.KWSummaryResponse;
import org.hscieripple.patient.keyworkers.KeyWorkerServiceSoap;
import org.hscieripple.patient.keyworkers.PairOfKeyWorkersListKeyKWSummaryResultRow;
import org.hscieripple.patient.keyworkers.model.KeyWorkerDetails;
import org.hscieripple.patient.keyworkers.model.KeyWorkerSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEAlertsSearch extends AbstractHSCIEService implements AlertSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEAlertsSearch.class);

    private static final String OK = "OK";

    @Autowired
    private AlertServiceSoap alertService;

    @Override
    public List<AlertSummary> findAllAlerts(final String patientId, final List<DataSourceSummary> dataSources) {
        final List<AlertSummary> alerts = new ArrayList<>();

        final Long nhsNumber = convertPatientIdToLong(patientId);

        for (final DataSourceSummary summary : dataSources) {
            final List<AlertSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            alerts.addAll(results);
        }

        return alerts;
    }

    @Override
    public AlertDetails findAlert(final String patientId, final String alertId, final String subSource) {
        AlertsDetailsResponse response = new AlertsDetailsResponse();

        final Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = alertService.findAlertsDetailsBO(nhsNumber, alertId, subSource);

            if (!isSuccessfulDetailsResponse(response)) {
                new AlertsDetailsResponse();
            }
        }
        catch (final SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new AlertDetailsResponseToAlertDetailsTransformer().transform(response);
    }

    private List<AlertSummary> makeSummaryCall(final Long nhsNumber, final String source) {
        List<PairOfAlertsListKeyAlertsSummaryResultRow> results = new ArrayList<>();

        try {
            final AlertsSummaryResponse response = alertService.findAlertsSummariesBO(nhsNumber);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getAlertsList().getAlertsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new AlertSummaryResponseToAlertSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(final AlertsSummaryResponse response) {
    	return OK.equalsIgnoreCase(response.getStatusCode());
    }

    private boolean isSuccessfulDetailsResponse(final AlertsDetailsResponse response) {
    	return OK.equalsIgnoreCase(response.getStatusCode());
    }
}
