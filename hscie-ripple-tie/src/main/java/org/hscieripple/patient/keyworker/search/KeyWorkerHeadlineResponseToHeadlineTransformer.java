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

package org.hscieripple.patient.keyworker.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.keyworkers.KWHeadlineResultRow;
import org.rippleosi.patient.contacts.model.ContactHeadline;

public class KeyWorkerHeadlineResponseToHeadlineTransformer implements Transformer<KWHeadlineResultRow, ContactHeadline> {
    
    @Override
    public ContactHeadline transform(KWHeadlineResultRow response) {

    	ContactHeadline headline = new ContactHeadline();
        headline.setSource(response.getDataSourceName());
        headline.setSourceId(response.getSourceID());
        headline.setName(response.getName());

        return headline;
    }
}
