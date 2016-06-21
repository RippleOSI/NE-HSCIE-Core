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

package org.hscieripple.patient.referrals.rest;
import java.util.List;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.referrals.model.HSCIEReferralDetails;
import org.hscieripple.patient.referrals.model.HSCIEReferralSummary;
import org.hscieripple.patient.referrals.search.HSCIEReferralSearch;
import org.hscieripple.patient.referrals.search.HSCIEReferralSearchFactory;
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
@RequestMapping("hscie/patients/{patientId}/referrals")
public class HSCIEReferralController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEReferralSearchFactory HSCIEReferralSearchFactory;    	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<HSCIEReferralSummary> findAllReferrals(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
		final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "referrals");

        HSCIEReferralSearch HSCIEReferralSearch = HSCIEReferralSearchFactory.select(sourceType);
        return HSCIEReferralSearch.findAllReferrals(patientId, dataSources);
    }

    @RequestMapping(value = "/{referralId}", method = RequestMethod.GET)
    public HSCIEReferralDetails findReferral(@PathVariable("patientId") String patientId,
                                          @PathVariable("referralId") String referralId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEReferralSearch HSCIEReferralSearch = HSCIEReferralSearchFactory.select(sourceType);

        return HSCIEReferralSearch.findReferral(patientId, referralId, subSource);
    }
    
}
