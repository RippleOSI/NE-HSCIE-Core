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

package org.hscieripple.patient.problems.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.problems.ProblemsDetailsResponse;
import org.hscieripple.patient.problems.ProblemsHeadlineResponse;
import org.hscieripple.patient.problems.ProblemsSummaryResponse;
import org.hscieripple.patient.problems.ProblemServiceSoap;
import org.hscieripple.patient.problems.PairOfProblemsListKeyProblemsHeadlineResultRow;
import org.hscieripple.patient.problems.PairOfProblemsListKeyProblemsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.problems.model.HSCIEProblemDetails;
import org.hscieripple.patient.problems.model.HSCIEProblemSummary;
import org.hscieripple.patient.problems.search.HSCIEProblemSearch;
import org.rippleosi.patient.problems.model.ProblemHeadline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEProblemsSearch extends AbstractHSCIEService implements HSCIEProblemSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEProblemsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private ProblemServiceSoap problemsService;

    @Override
    public List<HSCIEProblemSummary> findAllProblems(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEProblemSummary> problems = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEProblemSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            problems.addAll(results);
        }

        return problems;
    }
    
    
    
    
    @Override
    public List<ProblemHeadline> findAllProblemHeadlines(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<ProblemHeadline> problems = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<ProblemHeadline> results = makeHeadlineCall(nhsNumber, summary.getSourceId());

            problems.addAll(results);
        }

        return problems;
    }
    
    
    

    @Override
    public HSCIEProblemDetails findProblem(String patientId, String problemId, String source) {
    	ProblemsDetailsResponse response = new ProblemsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = problemsService.findProblemsDetailsBO(nhsNumber, problemId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEProblemDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ProblemDetailsResponseToDetailsTransformer().transform(response);
    }
    
    private List<ProblemHeadline> makeHeadlineCall(Long nhsNumber, String source) {
        List<PairOfProblemsListKeyProblemsHeadlineResultRow> results = new ArrayList<>();

        try {
            ProblemsHeadlineResponse response = problemsService.findProblemsHeadlinesBO(nhsNumber, source);

            if (isSuccessfulHeadlineResponse(response)) {
                results = response.getProblemsList().getProblemsHeadlineResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ProblemsHeadlineResponseToHeadlineTransformer(), new ArrayList<>());
    }

    private List<HSCIEProblemSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfProblemsListKeyProblemsSummaryResultRow> results = new ArrayList<>();

        try {
            ProblemsSummaryResponse response = problemsService.findProblemsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getProblemsList().getProblemsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }
        											
        return CollectionUtils.collect(results, new ProblemResponseToProblemSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ProblemsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
    
    private boolean isSuccessfulHeadlineResponse(ProblemsHeadlineResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(ProblemsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
