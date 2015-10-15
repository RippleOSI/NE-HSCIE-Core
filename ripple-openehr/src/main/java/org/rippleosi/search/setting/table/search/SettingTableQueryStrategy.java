package org.rippleosi.search.setting.table.search;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.rippleosi.common.service.C4HUriQueryStrategy;
import org.rippleosi.patient.summary.model.PatientSummary;
import org.rippleosi.search.common.model.SearchTablePatientDetails;
import org.rippleosi.search.setting.table.model.OpenEHRSettingRequestBody;
import org.rippleosi.search.setting.table.model.OpenEHRSettingResponse;
import org.rippleosi.search.setting.table.model.SettingTableQuery;
import org.rippleosi.search.setting.table.model.SettingTableResults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SettingTableQueryStrategy
    implements C4HUriQueryStrategy<OpenEHRSettingResponse[], SettingTableResults> {

    @Value("${c4hOpenEHR.address}")
    private String c4hOpenEHRAddress;

    @Value("${c4hOpenEHR.subjectNamespace}")
    private String externalNamespace;

    private List<PatientSummary> patientSummaries;
    private SettingTableQuery tableQuery;

    public void setPatientSummaries(List<PatientSummary> patientSummaries) {
        this.patientSummaries = patientSummaries;
    }

    public void setTableQuery(SettingTableQuery tableQuery) {
        this.tableQuery = tableQuery;
    }

    @Override
    public UriComponents getQueryUriComponents() {
        Integer pageNumber = Integer.valueOf(tableQuery.getPageNumber()) - 1;
        Integer pageSize = 15;

        return UriComponentsBuilder
            .fromHttpUrl(c4hOpenEHRAddress + "/view/rippleDash")
            .queryParam("orderBy", "NHSNumber")
            .queryParam("descending", tableQuery.getOrderType().equals("DESC"))
            .queryParam("offset", pageNumber * pageSize)
            .queryParam("limit", pageSize)
            .build();
    }

    @Override
    public Object getRequestBody() {
        OpenEHRSettingRequestBody body = new OpenEHRSettingRequestBody();

        // create a CSV list of NHS Numbers for OpenEHR to query associated data
        StringJoiner csvBuilder = new StringJoiner(",");

        for (PatientSummary patient : patientSummaries) {
            csvBuilder.add(patient.getNhsNumber());
        }

        body.setExternalIds(csvBuilder.toString());
        body.setExternalNamespace(externalNamespace);
        return body;
    }

    @Override
    public SettingTableResults transform(OpenEHRSettingResponse[] resultSet) {
        SettingTableResults results = new SettingTableResults();
        List<SearchTablePatientDetails> details = CollectionUtils.collect(patientSummaries,
                                                                          new SettingTablePatientDetailsTransformer(resultSet),
                                                                          new ArrayList<>());
        results.setPatientDetails(details);
        return results;
    }
}
