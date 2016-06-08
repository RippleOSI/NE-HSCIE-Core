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

package org.hscieripple.patient.referrals.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.referrals.ReferralsDetailsResponse;
import org.hscieripple.patient.referrals.ReferralsSummaryResponse;
import org.hscieripple.patient.referrals.ReferralServiceSoap;
import org.hscieripple.patient.referrals.PairOfReferralsListKeyReferralsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.referrals.model.HSCIEReferralDetails;
import org.hscieripple.patient.referrals.model.HSCIEReferralSummary;
import org.hscieripple.patient.referrals.search.HSCIEReferralSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEReferralsSearch extends AbstractHSCIEService implements HSCIEReferralSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEReferralSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private ReferralServiceSoap referralsService;

    @Override
    public List<HSCIEReferralSummary> findAllReferrals(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEReferralSummary> referrals = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEReferralSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            referrals.addAll(results);
        }

        return referrals;
    }

    @Override
    public HSCIEReferralDetails findReferral(String patientId, String referralId, String source) {
        ReferralsDetailsResponse response = new ReferralsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = referralsService.findReferralsDetailsBO(nhsNumber, referralId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEReferralDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ReferralDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEReferralSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfReferralsListKeyReferralsSummaryResultRow> results = new ArrayList<>();

        try {
            ReferralsSummaryResponse response = referralsService.findReferralsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getReferralsList().getReferralsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ReferralResponseToReferralSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ReferralsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(ReferralsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
