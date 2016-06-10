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

package org.hscieripple.patient.problems.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.problems.ProblemsSummaryResultRow;
import org.hscieripple.patient.problems.model.HSCIEProblemSummary;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class ProblemResponseToProblemSummaryTransformer implements Transformer<ProblemsSummaryResultRow, HSCIEProblemSummary> {

    @Override
    public HSCIEProblemSummary transform(ProblemsSummaryResultRow response) {
    	HSCIEProblemSummary summary = new HSCIEProblemSummary();
    	
    	Date dateOfOnset = HSCIEDateFormatter.toDate(response.getDateOfOnset(),response.getDataSourceName());
    	
        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());

        summary.setProblem(response.getProblem());
        summary.setDateOfOnset(dateOfOnset);

        return summary;
    }
}
