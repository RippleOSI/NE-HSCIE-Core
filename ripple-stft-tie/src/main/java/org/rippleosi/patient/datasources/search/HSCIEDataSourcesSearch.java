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
package org.rippleosi.patient.datasources.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.datasources.search.DataSourceResponse;
import org.hscieripple.patient.datasources.search.DataSourcesServiceSoap;
import org.hscieripple.patient.datasources.search.PairOfResultsSetKeyDSResultRow;
import org.rippleosi.common.service.AbstractHSCIEService;
import org.rippleosi.patient.datasources.model.DataSourceSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEDataSourcesSearch extends AbstractHSCIEService implements DataSourcesSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEDataSourcesSearch.class);

    @Autowired
    private DataSourcesServiceSoap dataSourcesService;

    @Override
    public List<DataSourceSummary> findAvailableDataSources(String patientId, String dataType) {
        try {
            DataSourceResponse response = dataSourcesService.findAvailableDSBO(dataType, convertPatientIdToLong(patientId));

            List<PairOfResultsSetKeyDSResultRow> dataSources = response.getResultsSet().getDSResultRow();

            return CollectionUtils.collect(dataSources, new DataSourceResponseToSummaryTransformer(), new ArrayList<>());
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage());

            return new ArrayList<>();
        }
    }
}
