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

package org.hscieripple.patient.allergies.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.allergies.AllergiesSummaryResultRow;
import org.hscieripple.patient.allergies.model.HSCIEAllergySummary;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class AllergiesResponseToAllergiesSummaryTransformer implements Transformer<AllergiesSummaryResultRow, HSCIEAllergySummary> {

    @Override
    public HSCIEAllergySummary transform(AllergiesSummaryResultRow response) {
    	HSCIEAllergySummary summary = new HSCIEAllergySummary();
    	    	
        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());
        
        summary.setCause(response.getCause());
        summary.setReaction(response.getReaction());
        

        return summary;
    }
}
