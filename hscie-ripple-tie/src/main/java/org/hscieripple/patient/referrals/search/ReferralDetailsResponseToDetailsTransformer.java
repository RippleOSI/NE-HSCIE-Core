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

package org.hscieripple.patient.referrals.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.referrals.ReferralsDetailsResponse;
import org.hscieripple.patient.referrals.model.HSCIEReferralDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class ReferralDetailsResponseToDetailsTransformer implements Transformer<ReferralsDetailsResponse, HSCIEReferralDetails> {

    @Override
    public HSCIEReferralDetails transform(ReferralsDetailsResponse response) {
    	 
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(), response.getDataSourceName());
		Date dateOfReferral = HSCIEDateFormatter.toDate(response.getDateOfReferral(), response.getDataSourceName());
		Date timeOfReferral = HSCIEDateFormatter.toDate(response.getTimeOfReferral(), response.getDataSourceName());
    	    	
		HSCIEReferralDetails details = new HSCIEReferralDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
            
			
        details.setAuthor(response.getAuthor());
		details.setDateCreated(dateCreated);
        details.setServiceTeam(response.getServiceTeam());
		details.setDateOfReferral(dateOfReferral);
		details.setTimeOfReferral(timeOfReferral);
		
        details.setLocation(response.getLocation());
		details.setStatus(response.getStatus());

        return details;
    }
}

