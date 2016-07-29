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
import org.rippleosi.patient.labresults.model.LabResultDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;
import java.util.List;
import org.apache.commons.collections4.MapUtils;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.Collections;
import java.util.ArrayList;

public class ResultsDetailsResponseToDetailsTransformer implements Transformer<ResultsDetailsResponse, LabResultDetails> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultsDetailsResponseToDetailsTransformer.class);
	
    @Override
    public LabResultDetails transform(ResultsDetailsResponse response) {
    	Date sampleTaken = HSCIEDateFormatter.toDate(response.getSampleTaken(),response.getDataSourceName());
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(),response.getDataSourceName());
    	
    	LabResultDetails details = new LabResultDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
        
        details.setTestName(response.getTestName());
        details.setSampleTaken(sampleTaken);
        details.setDateCreated(dateCreated);
        details.setStatus(response.getStatus());
        details.setConclusion(response.getConclusion());
        details.setAuthor(response.getAuthor());
        
        List<LabResultDetails.TestResult> testResults = createTestResults(response);

        details.setTestResults(testResults);
        
        
        return details;
    }
    
    private List<LabResultDetails.TestResult> createTestResults(ResultsDetailsResponse response) {

        List<ResultsDetailsResponse> labResults = extractLabResults(response);

        return CollectionUtils.collect(labResults, new ResultsDetailsTableTransformer(), new ArrayList<>());
    }
    
	private List<ResultsDetailsResponse> extractLabResults(ResultsDetailsResponse response) {
        try {
            return (List<ResultsDetailsResponse>)PropertyUtils.getNestedProperty(response, "test_panel.items");
        } catch (Exception ex) {
            LOGGER.debug("{}: {}", ex.getClass().getName(), ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }
}

