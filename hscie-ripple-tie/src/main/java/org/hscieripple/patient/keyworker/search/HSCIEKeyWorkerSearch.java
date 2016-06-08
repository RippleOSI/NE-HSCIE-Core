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

package org.hscieripple.patient.keyworker.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.keyworkers.KWDetailsResponse;
import org.hscieripple.patient.keyworkers.KWSummaryResponse;
import org.hscieripple.patient.keyworkers.KeyWorkerServiceSoap;
import org.hscieripple.patient.keyworkers.PairOfKeyWorkersListKeyKWSummaryResultRow;
import org.hscieripple.patient.keyworkers.model.KeyWorkerDetails;
import org.hscieripple.patient.keyworkers.model.KeyWorkerSummary;
import org.hscieripple.patient.keyworkers.search.KeyWorkerSearch;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEKeyWorkerSearch extends AbstractHSCIEService implements KeyWorkerSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEKeyWorkerSearch.class);

    private static final String OK = "OK";

    @Autowired
    private KeyWorkerServiceSoap keyWorkersService;

    @Override
    public List<KeyWorkerSummary> findAllKeyWorkers(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<KeyWorkerSummary> keyWorkers = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<KeyWorkerSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            keyWorkers.addAll(results);
        }

        return keyWorkers;
    }

    @Override
    public KeyWorkerDetails findKeyWorker(String patientId, String keyWorkerId, String source) {
        KWDetailsResponse response = new KWDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = keyWorkersService.findKWDetailsBO(nhsNumber, keyWorkerId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new KeyWorkerDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new KeyWorkerDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<KeyWorkerSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfKeyWorkersListKeyKWSummaryResultRow> results = new ArrayList<>();

        try {
            KWSummaryResponse response = keyWorkersService.findKWSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getKeyWorkersList().getKWSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new KeyWorkerResponseToKeyWorkerSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(KWSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }

    private boolean isSuccessfulDetailsResponse(KWDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }
}
