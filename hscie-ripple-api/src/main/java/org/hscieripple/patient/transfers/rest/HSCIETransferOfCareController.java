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
package org.hscieripple.patient.transfers.rest;
import java.util.List;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.transfers.model.HSCIETransferOfCareDetails;
import org.hscieripple.patient.transfers.model.HSCIETransferOfCareSummary;
import org.hscieripple.patient.transfers.search.HSCIETransferOfCareSearch;
import org.hscieripple.patient.transfers.search.HSCIETransferOfCareSearchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.hscieripple.common.types.HSCIERepoSourceTypes; 
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory; 
import org.rippleosi.common.types.RepoSourceType;

@RestController
@RequestMapping("/patients/{patientId}/hscie-transfers")
public class HSCIETransferOfCareController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIETransferOfCareSearchFactory HSCIETransferOfCareSearchFactory;    	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<HSCIETransferOfCareSummary> findAllTransfers(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
        DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "transfers");

        final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
        HSCIETransferOfCareSearch HSCIETransferOfCareSearch = HSCIETransferOfCareSearchFactory.select(sourceType);
        return HSCIETransferOfCareSearch.findAllTransfers(patientId, dataSources);
    }

    @RequestMapping(value = "/{transferId}", method = RequestMethod.GET)
    public HSCIETransferOfCareDetails findTransfer(@PathVariable("patientId") String patientId,
                                          @PathVariable("transferId") String transferId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIETransferOfCareSearch HSCIETransferOfCareSearch = HSCIETransferOfCareSearchFactory.select(sourceType);

        return HSCIETransferOfCareSearch.findTransfer(patientId, transferId, subSource);
    }
    
}
