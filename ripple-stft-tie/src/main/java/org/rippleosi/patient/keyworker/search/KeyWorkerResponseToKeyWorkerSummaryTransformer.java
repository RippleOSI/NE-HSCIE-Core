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

package org.rippleosi.patient.keyworker.search;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.keyworkers.KWSummaryResultRow;
import org.rippleosi.patient.keyworkers.model.KeyWorkerSummary;

public class KeyWorkerResponseToKeyWorkerSummaryTransformer implements Transformer<KWSummaryResultRow, KeyWorkerSummary> {

    @Override
    public KeyWorkerSummary transform(KWSummaryResultRow response) {
        KeyWorkerSummary summary = new KeyWorkerSummary();

        summary.setSource("tie");
        summary.setSourceId(response.getDataSourceName());

        summary.setContactNumber(response.getContactNumber());
        summary.setName(response.getForename() + " " + response.getSurname());
        summary.setRole(response.getRole());

        return summary;
    }
}
