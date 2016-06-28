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

package org.hscieripple.patient.results.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.results.ResultsDetailsResponse;
import org.hscieripple.patient.results.ResultsSummaryResponse;
import org.hscieripple.patient.results.ResultServiceSoap;
import org.hscieripple.patient.results.PairOfResultsListKeyResultsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.results.model.HSCIEResultDetails;
import org.hscieripple.patient.results.model.HSCIEResultSummary;
import org.hscieripple.patient.results.search.HSCIEResultSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEResultsSearch extends AbstractHSCIEService implements HSCIEResultSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEResultsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private ResultServiceSoap resultsService;

    @Override
    public List<HSCIEResultSummary> findAllResults(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEResultSummary> resultsList = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEResultSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            results.addAll(results);
        }

        return resultsList;
    }

    @Override
    public HSCIEResultDetails findResult(String patientId, String resultId, String source) {
        ResultsDetailsResponse response = new ResultsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = resultsService.findResultsDetailsBO(nhsNumber, resultId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEResultDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ResultsDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEResultSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfResultsListKeyResultsSummaryResultRow> results = new ArrayList<>();

        try {
            ResultsSummaryResponse response = resultsService.findResultsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getResultsList().getResultsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ResultsResponseToResultsSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ResultsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(ResultsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
