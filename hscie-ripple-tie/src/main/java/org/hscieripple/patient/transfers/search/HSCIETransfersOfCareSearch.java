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

package org.hscieripple.patient.transfers.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.transfers.TransferOfCareDetailsResponse;
import org.hscieripple.patient.transfers.TransferOfCareHeadlineResponse;
import org.hscieripple.patient.transfers.TransferOfCareSummaryResponse;
import org.hscieripple.patient.transfers.TransferOfCareServiceSoap;
import org.hscieripple.patient.transfers.PairOfResultsSetKeyTransferOfCareSummaryResultRow;
import org.hscieripple.patient.transfers.PairOfTransferOfCareListKeyTransferOfCareHeadlineResultRow;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.transfers.model.HSCIETransferOfCareDetails;
import org.hscieripple.patient.transfers.model.HSCIETransferOfCareSummary;
import org.hscieripple.patient.transfers.search.HSCIETransferOfCareSearch;
import org.rippleosi.patient.summary.model.TransferHeadline;
import org.hscieripple.patient.transfers.search.TransferOfCareHeadlineResponseToHeadlineTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIETransfersOfCareSearch extends AbstractHSCIEService implements HSCIETransferOfCareSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIETransfersOfCareSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private TransferOfCareServiceSoap transfersService;

    @Override
    public List<HSCIETransferOfCareSummary> findAllTransfers(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIETransferOfCareSummary> transfers = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIETransferOfCareSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            transfers.addAll(results);
        }

        return transfers;
    }
    
    
    
    @Override
    public List<TransferHeadline> findAllTransferOfCareHeadlines(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<TransferHeadline> transfers = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<TransferHeadline> results = makeHeadlineCall(nhsNumber, summary.getSourceId());

            transfers.addAll(results);
        }

        return transfers;
    }
    
    

    @Override
    public HSCIETransferOfCareDetails findTransfer(String patientId, String transferId, String source) {
        TransferOfCareDetailsResponse response = new TransferOfCareDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = transfersService.findTransferOfCareDetailsBO(nhsNumber, transferId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIETransferOfCareDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new TransferOfCareDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIETransferOfCareSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfResultsSetKeyTransferOfCareSummaryResultRow> results = new ArrayList<>();

        try {
            TransferOfCareSummaryResponse response = transfersService.findTransferOfCareSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getResultsSet().getTransferOfCareSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new TransferOfCareResponseToTransferOfCareSummaryTransformer(), new ArrayList<>());
    }
    
    
    
    
    
    
    private List<TransferHeadline> makeHeadlineCall(Long nhsNumber, String source) {
        List<PairOfTransferOfCareListKeyTransferOfCareHeadlineResultRow> results = new ArrayList<>();

        try {
        	TransferOfCareHeadlineResponse response = transfersService.findTransferOfCareHeadlinesBO(nhsNumber, source);

            if (isSuccessfulHeadlineResponse(response)) {
                results = response.getTransferOfCareList().getTransferOfCareHeadlineResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new TransferOfCareHeadlineResponseToHeadlineTransformer(), new ArrayList<>());
    }
    
    
    
    
    
    

    private boolean isSuccessfulSummaryResponse(TransferOfCareSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
    
    private boolean isSuccessfulHeadlineResponse(TransferOfCareHeadlineResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }    

    private boolean isSuccessfulDetailsResponse(TransferOfCareDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
