/*
 * Copyright 2016 Ripple OSI
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

package org.hscieripple.patient.consent;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.consent.model.ConsentDetails;
import org.hscieripple.patient.consent.model.ConsentSummary;
import org.hscieripple.patient.consent.search.ConsentSearch;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.SOAPFaultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HSCIEConsentSearch extends AbstractHSCIEService implements ConsentSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEConsentSearch.class);

    private static final String OK = "OK";

    @Autowired
    private ConsentFindAllServiceSoap consentFindAllService;

    @Autowired
    private ConsentFindSingleServiceSoap consentFindOneService;

    @Override
    public List<ConsentSummary> findAllConsents(String patientId, List<DataSourceSummary> datasourceSummaries) {
        Long nhsNumber = convertPatientIdToLong(patientId);
        List<ConsentSummary> results = makeSummaryCall(nhsNumber, null);

        return results;
    }

    @Override
    public ConsentDetails findConsent(String patientId, String consentId, String source) {
        ConsentFindSingleResponse response = new ConsentFindSingleResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = consentFindOneService.findConsentFindSingleBO(nhsNumber, consentId);

            if (!isSuccessfulDetailsResponse(response)) {
                return new ConsentDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ConsentResponseToConsentDetailsTransformer().transform(response);
    }

    private List<ConsentSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfConsentsListKeyConsentFindAllResultRow> results = new ArrayList<>();

        try {
            ConsentFindAllResponse response = consentFindAllService.findConsentFindAllBO(nhsNumber);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getConsentsList().getConsentFindAllResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ConsentResponseToConsentSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ConsentFindAllResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }

    private boolean isSuccessfulDetailsResponse(ConsentFindSingleResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }
}


