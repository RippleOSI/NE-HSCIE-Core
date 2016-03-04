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

package org.rippleosi.patient.keyworker.search;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.keyworker.KWSummariesServiceSoap;
import org.hscieripple.patient.keyworker.KWSummaryResponse;
import org.hscieripple.patient.keyworker.PairOfKeyWorkersListKeyKWResultRow;
import org.rippleosi.common.service.AbstractHSCIEService;
import org.rippleosi.patient.datasources.model.DatasourceSummary;
import org.rippleosi.patient.keyworkers.model.KeyWorkerDetails;
import org.rippleosi.patient.keyworkers.model.KeyWorkerSummary;
import org.rippleosi.patient.keyworkers.search.KeyWorkerSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class HSCIEKeyWorkerSearch extends AbstractHSCIEService implements KeyWorkerSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEKeyWorkerSearch.class);

    @Autowired
    private KWSummariesServiceSoap kwSummariesService;

    @Override
    public List<KeyWorkerSummary> findAllKeyWorkers(String patientId, List<DatasourceSummary> datasourceSummaries) {
        Long nhsNumber = patientId == null ? null : Long.valueOf(patientId);

        //TODO test needs removing to add asynchronous call for all data sources
        try {
            KWSummaryResponse kwSummaryResponse = kwSummariesService.findKWSummariesBO(nhsNumber, "test");

            List<PairOfKeyWorkersListKeyKWResultRow> kwResultRow = kwSummaryResponse.getKeyWorkersList().getKWResultRow();

            return CollectionUtils.collect(kwResultRow, new KeyWorkerResponseToKeyWorkerSummaryTransformer(), new ArrayList<>());
        } catch (SOAPFaultException e) {
            log.error(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Override
    public KeyWorkerDetails findKeyWorker(String patientId, String keyWorkerId, List<DatasourceSummary> datasourceSummaries) {
        //TODO awaiting wsdl
        return null;
    }
}
