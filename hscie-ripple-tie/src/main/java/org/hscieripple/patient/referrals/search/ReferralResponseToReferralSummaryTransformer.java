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

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.referrals.ReferralsSummaryResultRow;
import org.hscieripple.patient.referrals.model.HSCIEReferralSummary;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class ReferralResponseToReferralSummaryTransformer implements Transformer<ReferralsSummaryResultRow, HSCIEReferralSummary> {

    @Override
    public HSCIEReferralSummary transform(ReferralsSummaryResultRow response) {
    	HSCIEReferralSummary summary = new HSCIEReferralSummary();
    	
    	Date dateOfReferral = HSCIEDateFormatter.toDate(response.getDateOfReferral(),response.getDataSourceName());
		
        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());
        
        summary.setDateOfReferral(dateOfReferral);
        summary.setReferralFrom(response.getReferralFrom());
        summary.setReferralTo(response.getReferralTo());

        return summary;
    }
}
