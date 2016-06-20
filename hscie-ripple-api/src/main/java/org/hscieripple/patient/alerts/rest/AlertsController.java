package org.hscieripple.patient.alerts.rest;

import java.util.List;

import org.hscieripple.patient.alerts.model.AlertDetails;
import org.hscieripple.patient.alerts.model.AlertSummary;
import org.hscieripple.patient.alerts.search.AlertSearch;
import org.hscieripple.patient.alerts.search.AlertSearchFactory;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients/{patientId}/appointments")
public class AlertsController {

    @Autowired
	private RepoSourceLookupFactory repoSourceLookup;

	@Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @Autowired
    private AlertSearchFactory alertSearchFactory;

	@RequestMapping(method = RequestMethod.GET)
    public List<AlertSummary> findAllAlerts(@PathVariable("patientId") String patientId,
                                            @RequestParam(required = false) String source) {
		final RepoSourceType sourceType = repoSourceLookup.lookup(source);

    	final DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(sourceType);

        final List<DataSourceSummary> dataSources = dataSourcesSearch.findAvailableDataSources(patientId, "alerts");

        final AlertSearch alertSearch = alertSearchFactory.select(sourceType);

        return alertSearch.findAllAlerts(patientId, dataSources);
    }

    @RequestMapping(value = "/{alertId}", method = RequestMethod.GET)
    public AlertDetails findAlert(@PathVariable("patientId") String patientId,
                                  @PathVariable("alertId") String alertId,
                                  @RequestParam(required = false) String source,
                                  @RequestParam(required = false) String subSource) {
    	final RepoSourceType sourceType = repoSourceLookup.lookup(source);

    	final AlertSearch alertSearch = alertSearchFactory.select(sourceType);

        return alertSearch.findAlert(patientId, alertId, subSource);
    }
}
