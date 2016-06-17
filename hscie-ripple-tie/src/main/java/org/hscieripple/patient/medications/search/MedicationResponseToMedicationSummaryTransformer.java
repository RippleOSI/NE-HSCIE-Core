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

package org.hscieripple.patient.medications.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.medications.MedicationsSummaryResultRow;
import org.hscieripple.patient.medications.model.HSCIEMedicationSummary;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class MedicationResponseToMedicationSummaryTransformer implements Transformer<MedicationsSummaryResultRow, HSCIEMedicationSummary> {

    @Override
    public HSCIEMedicationSummary transform(MedicationsSummaryResultRow response) {
    	HSCIEMedicationSummary summary = new HSCIEMedicationSummary();
    	
    	
        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());

        summary.setName(response.getName());
        summary.setDoseAmount(response.getDoseAmount());

        return summary;
    }
}
