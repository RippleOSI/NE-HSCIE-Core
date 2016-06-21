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

package org.hscieripple.patient.contact.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.contacts.ContactsSummaryResultRow;
import org.hscieripple.patient.contacts.model.HSCIEContactSummary;

public class ContactsResponseToContactsSummaryTransformer implements Transformer<ContactsSummaryResultRow, HSCIEContactSummary> {

    @Override
    public HSCIEContactSummary transform(ContactsSummaryResultRow response) {
    	HSCIEContactSummary summary = new HSCIEContactSummary();

        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());

        summary.setName(response.getName());
        summary.setRelationship(response.getRelationship());
        summary.setNextOfKin(response.isNextOfKin());

        return summary;
    }
}
