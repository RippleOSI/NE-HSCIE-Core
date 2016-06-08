/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the MedicationSTFT License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.MedicationSTFT.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.medications.rest;

import java.util.List;
import org.hscieripple.common.types.HSCIERepoSourceTypes; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.medications.model.HSCIEMedicationDetails;
import org.hscieripple.patient.medications.model.HSCIEMedicationSummary;
import org.hscieripple.patient.medications.search.HSCIEMedicationSearch;
import org.hscieripple.patient.medications.search.HSCIEMedicationSearchFactory;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hscie/patients/{patientId}/medications")
public class HSCIEMedicationController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEMedicationSearchFactory HSCIEMedicationSearchFactory;    	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<HSCIEMedicationSummary> findAllMedications(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
		final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "medications");

        HSCIEMedicationSearch HSCIEMedicationSearch = HSCIEMedicationSearchFactory.select(sourceType);
        return HSCIEMedicationSearch.findAllMedications(patientId, dataSources);
    }

    @RequestMapping(value = "/{medicationId}", method = RequestMethod.GET)
    public HSCIEMedicationDetails findMedication(@PathVariable("patientId") String patientId,
                                          @PathVariable("medicationId") String medicationId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEMedicationSearch HSCIEMedicationSearch = HSCIEMedicationSearchFactory.select(sourceType);

        return HSCIEMedicationSearch.findMedication(patientId, medicationId, subSource);
    }
    
}
