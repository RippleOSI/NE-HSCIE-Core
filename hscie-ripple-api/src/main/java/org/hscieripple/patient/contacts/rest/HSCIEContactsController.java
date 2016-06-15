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
package org.hscieripple.patient.contacts.rest;
import java.util.List;

import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.contacts.model.HSCIEContactDetails;
import org.hscieripple.patient.contacts.model.HSCIEContactSummary;
import org.rippleosi.patient.contacts.model.ContactHeadline;
import org.hscieripple.patient.contacts.search.HSCIEContactSearch;
import org.hscieripple.patient.contacts.search.HSCIEContactSearchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.hscieripple.common.types.HSCIERepoSourceTypes; 
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;  
import org.rippleosi.common.types.RepoSourceType;

@RestController
@RequestMapping("hscie/patients/{patientId}/contacts")
public class HSCIEContactsController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

    @Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEContactSearchFactory HSCIEContactSearchFactory;

    @RequestMapping(method = RequestMethod.GET)
    public List<HSCIEContactSummary> findAllContacts(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "contacts");

        HSCIEContactSearch HSCIEContactSearch = HSCIEContactSearchFactory.select(sourceType);
        return HSCIEContactSearch.findAllContacts(patientId, dataSources);
    }
    
    @RequestMapping(value = "/headlines", method = RequestMethod.GET)
    public List<ContactHeadline> findContactHeadlines(@PathVariable("patientId") String patientId,
                                                      @RequestParam(required = false) String source) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
    	List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "contacts");
    	
        HSCIEContactSearch HSCIEContactSearch = HSCIEContactSearchFactory.select(sourceType);
        return HSCIEContactSearch.findContactHeadlines(patientId, dataSources);
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.GET)
    public HSCIEContactDetails findContact(@PathVariable("patientId") String patientId,
                                          @PathVariable("contactId") String contactId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEContactSearch HSCIEContactSearch = HSCIEContactSearchFactory.select(sourceType);

        return HSCIEContactSearch.findContact(patientId, contactId, subSource);
    }
}
