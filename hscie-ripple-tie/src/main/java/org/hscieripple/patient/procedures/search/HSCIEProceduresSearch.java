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

package org.hscieripple.patient.procedures.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.procedures.ProceduresDetailsResponse;
import org.hscieripple.patient.procedures.ProceduresSummaryResponse;
import org.hscieripple.patient.procedures.ProcedureServiceSoap;
import org.hscieripple.patient.procedures.PairOfProceduresListKeyProceduresSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.procedures.model.HSCIEProcedureDetails;
import org.hscieripple.patient.procedures.model.HSCIEProcedureSummary;
import org.hscieripple.patient.procedures.search.HSCIEProcedureSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEProceduresSearch extends AbstractHSCIEService implements HSCIEProcedureSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEProceduresSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private ProcedureServiceSoap proceduresService;

    @Override
    public List<HSCIEProcedureSummary> findAllProcedures(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEProcedureSummary> procedures = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEProcedureSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            procedures.addAll(results);
        }

        return procedures;
    }

    @Override
    public HSCIEProcedureDetails findProcedure(String patientId, String procedureId, String source) {
        ProceduresDetailsResponse response = new ProceduresDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = proceduresService.findProceduresDetailsBO(nhsNumber, procedureId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEProcedureDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ProceduresDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEProcedureSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfProceduresListKeyProceduresSummaryResultRow> results = new ArrayList<>();

        try {
            ProceduresSummaryResponse response = proceduresService.findProceduresSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getProceduresList().getProceduresSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ProceduresResponseToProceduresSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ProceduresSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(ProceduresDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
