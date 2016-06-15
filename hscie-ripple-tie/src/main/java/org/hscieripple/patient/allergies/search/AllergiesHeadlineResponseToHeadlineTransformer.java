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

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.allergies.AllergiesHeadlineResultRow;
import org.rippleosi.patient.summary.model.PatientHeadline;

public class AllergyHeadlineResponseToHeadlineTransformer implements Transformer<AllergiesHeadlineResultRow, PatientHeadline> {
    
    @Override
    public PatientHeadline transform(AllergiesHeadlineResultRow response) {

        PatientHeadline headline = new PatientHeadline();
        headline.setSource(response.getSource());
        headline.setSourceId(response.getSourceId());
        headline.setText(response.getText());

        return headline;
    }
}
