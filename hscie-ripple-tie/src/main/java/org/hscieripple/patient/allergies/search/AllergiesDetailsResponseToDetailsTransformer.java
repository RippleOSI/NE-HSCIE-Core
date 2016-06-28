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

package org.hscieripple.patient.allergies.search;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.common.util.HSCIEDateFormatter;
import org.hscieripple.patient.allergies.AllergiesDetailsResponse;
import org.rippleosi.patient.allergies.model.AllergyDetails;

public class AllergiesDetailsResponseToDetailsTransformer implements Transformer<AllergiesDetailsResponse, AllergyDetails> {

    @Override
    public AllergyDetails transform(AllergiesDetailsResponse response) {
    	
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(),response.getDataSourceName());
    	 
		AllergyDetails details = new AllergyDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
        
        details.setCause(response.getCause());
        details.setCauseCode(response.getCauseCode());
        details.setReaction(response.getReaction());
        details.setCauseTerminology(response.getCauseTerminology());
        details.setAuthor(response.getAuthor());
        details.setDateCreated(dateCreated);
        

        return details;
    }
}

