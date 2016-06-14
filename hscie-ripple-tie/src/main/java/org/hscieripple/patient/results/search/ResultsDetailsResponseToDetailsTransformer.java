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

package org.hscieripple.patient.results.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.results.ResultsDetailsResponse;
import org.hscieripple.patient.results.model.HSCIEResultDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class ResultsDetailsResponseToDetailsTransformer implements Transformer<ResultsDetailsResponse, HSCIEResultDetails> {

    @Override
    public HSCIEResultDetails transform(ResultsDetailsResponse response) {
    	 
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(), response.getDataSourceName());
    	    	
		HSCIEResultDetails details = new HSCIEResultDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());			
        
        details.setTestName(response.getTestName());
        details.setStatus(response.getStatus());
        details.setSampleTaken(response.getSampleTaken());
        details.setResult(response.getResult());
        details.setValue(response.getValue());
        details.setUnit(response.getUnit());
        details.setNormalRange(response.getNormalRange());
        details.setComment(response.getComment());
        details.setConclusion(response.getConclusion());        
		
		details.setAuthor(response.getAuthor());
		details.setDateCreated(dateCreated);

        return details;
    }
}

