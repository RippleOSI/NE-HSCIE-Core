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
package org.hscieripple.patient.allergies.rest;

import java.util.List;

import org.hscieripple.patient.allergies.search.HSCIEAllergySearch;
import org.hscieripple.patient.allergies.search.HSCIEAllergySearchFactory;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;
import org.rippleosi.patient.allergies.model.AllergyDetails;
import org.rippleosi.patient.allergies.model.AllergySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hscie/patients/{patientId}/allergies")
public class HSCIEAllergiesController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEAllergySearchFactory HSCIEAllergySearchFactory;    	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<AllergySummary> findAllAllergies(@PathVariable("patientId") String patientId,
                                                 @RequestParam(required = false) String source) {
		final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "allergies");

        HSCIEAllergySearch HSCIEAllergySearch = HSCIEAllergySearchFactory.select(sourceType);
        return HSCIEAllergySearch.findAllAllergies(patientId, dataSources);
    }

    @RequestMapping(value = "/{allergyId}", method = RequestMethod.GET)
    public AllergyDetails findAllergy(@PathVariable("patientId") String patientId,
                                      @PathVariable("allergyId") String allergyId,
                                      @RequestParam(required = false) String source,
                                      @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEAllergySearch HSCIEAllergySearch = HSCIEAllergySearchFactory.select(sourceType);

        return HSCIEAllergySearch.findAllergy(patientId, allergyId, subSource);
    }
    
}
