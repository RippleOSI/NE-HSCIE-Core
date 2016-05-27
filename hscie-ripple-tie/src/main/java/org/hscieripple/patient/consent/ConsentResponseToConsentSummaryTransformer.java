/*
 * Copyright 2016 Ripple OSI
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

package org.hscieripple.patient.consent;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.consent.model.ConsentSummary;

import java.util.Date;

/**
 */
public class ConsentResponseToConsentSummaryTransformer implements Transformer<ConsentFindAllResultRow, ConsentSummary> {

    @Override
    public ConsentSummary transform(ConsentFindAllResultRow response) {
        ConsentSummary summary = new ConsentSummary();

        summary.setSource("TIE");
        summary.setSourceId(response.getConsentID());
        //summary.setDateCreated(new Date(response.getConsentDate()));
        summary.setOptIn(response.isConsentStatus());

        return summary;
    }
}