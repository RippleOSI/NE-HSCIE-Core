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
package org.hscieripple.patient.problems.search;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.problems.ProblemsDetailsResponse;
import org.hscieripple.patient.problems.model.HSCIEProblemDetails;
import org.rippleosi.common.util.HSCIEDateFormatter;

public class ProblemDetailsResponseToDetailsTransformer implements Transformer<ProblemsDetailsResponse, HSCIEProblemDetails> {

    @Override
    public HSCIEProblemDetails transform(ProblemsDetailsResponse response) {
    	 
    	
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(),response.getDataSourceName());    
    	Date dateOfOnset = HSCIEDateFormatter.toDate(response.getDateOfOnset(),response.getDataSourceName());    
    	
    	    	
    	HSCIEProblemDetails details = new HSCIEProblemDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
            
        details.setProblem(response.getProblem());
        details.setDateOfOnset(dateOfOnset);
        details.setDescription(response.getDescription());
        details.setCode(response.getCode());
        details.setTerminology(response.getTerminology());
        details.setAuthor(response.getAuthor());
        details.setDateCreated(dateCreated);

        return details;
    }
}
