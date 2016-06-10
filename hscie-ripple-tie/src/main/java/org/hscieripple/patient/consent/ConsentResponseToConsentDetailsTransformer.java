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
import org.hscieripple.common.types.HSCIERepoSourceTypes;
import org.hscieripple.patient.consent.model.ConsentDetails;
import org.rippleosi.common.util.DateFormatter;

/**
 */
public class ConsentResponseToConsentDetailsTransformer implements Transformer<ConsentFindSingleResponse, ConsentDetails> {

    @Override
    public ConsentDetails transform(ConsentFindSingleResponse response) {
        final ConsentDetails details = new ConsentDetails();

        details.setSource(HSCIERepoSourceTypes.TIE.getSourceName());
        details.setSourceId(response.getConsentID());

        details.setAuthor(response.getConsentAuthor());
        details.setOptIn(response.isConsentStatus());
        details.setDateCreated(DateFormatter.toDate(response.getConsentDate()));
        details.setReason(response.getConsentReason());

        return details;
    }
}