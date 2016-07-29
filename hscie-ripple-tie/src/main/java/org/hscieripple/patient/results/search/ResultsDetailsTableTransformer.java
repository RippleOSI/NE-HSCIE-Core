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

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.results.ResultsDetailsResponse;
import org.rippleosi.patient.labresults.model.LabResultDetails;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.PropertyUtils;

public class ResultsDetailsTableTransformer implements Transformer<ResultsDetailsResponse, LabResultDetails.TestResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultsDetailsTableTransformer.class);

    private String getValueAsString(ResultsDetailsResponse response, String path) {
        try {
            Object value = PropertyUtils.getNestedProperty(response, path);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception ex) {
            LOGGER.debug("{}: {}", ex.getClass().getName(), ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public LabResultDetails.TestResult transform(ResultsDetailsResponse response) {

        LabResultDetails.TestResult testResult = new LabResultDetails.TestResult();

        String name = getValueAsString(response, "(items)[1].name.value");
        String value = getValueAsString(response, "(items)[1].value.magnitude");
        String units = getValueAsString(response, "(items)[1].value.units");
        String comment = getValueAsString(response, "(items)[1].value.value");

        testResult.setResult(name);
        testResult.setValue(value);
        testResult.setUnit(units);
        testResult.setComment(comment);

        return testResult;
    }
}

