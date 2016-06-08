/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the HSCIEAppointment License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.HSCIEAppointment.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.appointments.rest;
import java.util.List;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.appointments.model.HSCIEAppointmentDetails;
import org.hscieripple.patient.appointments.model.HSCIEAppointmentSummary;
import org.hscieripple.patient.appointments.search.HSCIEAppointmentSearch;
import org.hscieripple.patient.appointments.search.HSCIEAppointmentSearchFactory;
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
@RequestMapping("hscie/patients/{patientId}/appointments")
public class HSCIEAppointmentsController {

	@Autowired  
	private RepoSourceLookupFactory repoSourceLookup;  

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private HSCIEAppointmentSearchFactory HSCIEAppointmentSearchFactory;    	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<HSCIEAppointmentSummary> findAllAppointments(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
		final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);
        List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "appointments");

        HSCIEAppointmentSearch HSCIEAppointmentSearch = HSCIEAppointmentSearchFactory.select(sourceType);
        return HSCIEAppointmentSearch.findAllAppointments(patientId, dataSources);
    }

    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.GET)
    public HSCIEAppointmentDetails findAppointment(@PathVariable("patientId") String patientId,
                                          @PathVariable("appointmentId") String appointmentId,
                                          @RequestParam(required = false) String source,
                                          @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source); 
    	HSCIEAppointmentSearch HSCIEAppointmentSearch = HSCIEAppointmentSearchFactory.select(sourceType);

        return HSCIEAppointmentSearch.findAppointment(patientId, appointmentId, subSource);
    }
    
}
