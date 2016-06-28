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
package org.hscieripple.patient.problems.rest;
import java.util.List;

import org.hscieripple.patient.contacts.search.HSCIEContactSearch;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.problems.model.HSCIEProblemDetails;
import org.hscieripple.patient.problems.model.HSCIEProblemSummary;
import org.hscieripple.patient.problems.search.HSCIEProblemSearch;
import org.hscieripple.patient.problems.search.HSCIEProblemSearchFactory;
import org.rippleosi.patient.problems.model.ProblemHeadline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.hscieripple.common.types.HSCIERepoSourceTypes; 
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;
import org.rippleosi.patient.contacts.model.ContactHeadline;
import org.rippleosi.common.types.RepoSourceType;

@RestController
@RequestMapping("hscie/patients/{patientId}/problems")
public class HSCIEProblemsController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

    @Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEProblemSearchFactory HSCIEProblemSearchFactory;

    @RequestMapping(method = RequestMethod.GET)
    public List<HSCIEProblemSummary> findAllProblems(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "problems");

        HSCIEProblemSearch HSCIEProblemSearch = HSCIEProblemSearchFactory.select(sourceType);
        return HSCIEProblemSearch.findAllProblems(patientId, dataSources);
    }
    
    @RequestMapping(value = "/headlines", method = RequestMethod.GET)
    public List<ProblemHeadline> findAllProblemHeadlines(@PathVariable("patientId") String patientId,
                                                      @RequestParam(required = false) String source) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
    	List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "contacts");
    	
        HSCIEProblemSearch HSCIEProblemSearch = HSCIEProblemSearchFactory.select(sourceType);
        return HSCIEProblemSearch.findAllProblemHeadlines(patientId, dataSources);
    }

    @RequestMapping(value = "/{problemId}", method = RequestMethod.GET)
    public HSCIEProblemDetails findProblem(@PathVariable("patientId") String patientId,
                                          @PathVariable("problemId") String problemId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEProblemSearch HSCIEProblemSearch = HSCIEProblemSearchFactory.select(sourceType);

        return HSCIEProblemSearch.findProblem(patientId, problemId, subSource);
    }
}
