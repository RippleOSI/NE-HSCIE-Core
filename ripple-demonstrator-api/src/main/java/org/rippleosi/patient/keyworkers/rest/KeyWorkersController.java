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
package org.rippleosi.patient.keyworkers.rest;

import java.util.List;

import org.rippleosi.patient.datasources.model.DataSourceSummary;
import org.rippleosi.patient.datasources.search.DataSourcesSearch;
import org.rippleosi.patient.datasources.search.DataSourcesSearchFactory;
import org.rippleosi.patient.keyworkers.model.KeyWorkerDetails;
import org.rippleosi.patient.keyworkers.model.KeyWorkerSummary;
import org.rippleosi.patient.keyworkers.search.KeyWorkerSearch;
import org.rippleosi.patient.keyworkers.search.KeyWorkerSearchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients/{patientId}/keyworkers")
public class KeyWorkersController {

    @Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private KeyWorkerSearchFactory keyWorkerSearchFactory;

    @RequestMapping(method = RequestMethod.GET)
    public List<KeyWorkerSummary> findAllKeyWorkers(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
        DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "keyWorkers");

        KeyWorkerSearch keyWorkerSearch = keyWorkerSearchFactory.select(source);
        return keyWorkerSearch.findAllKeyWorkers(patientId, dataSources);
    }

    @RequestMapping(value = "/{keyWorkerId}", method = RequestMethod.GET)
    public KeyWorkerDetails findKeyWorker(@PathVariable("patientId") String patientId,
                                          @PathVariable("keyWorkerId") String keyWorkerId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
        KeyWorkerSearch keyWorkerSearch = keyWorkerSearchFactory.select(source);

        return keyWorkerSearch.findKeyWorker(patientId, keyWorkerId, subSource);
    }
}
