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
package org.hscieripple.patient.query.search;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.query.ResultRow;
import org.rippleosi.common.util.DateFormatter;
import org.rippleosi.patient.summary.model.PatientDetails;

public class PatientResponseToPatientDetailsTransformer implements Transformer<ResultRow, PatientDetails> {

    @Override
    public PatientDetails transform(ResultRow response) {

        String sourceId = String.valueOf(response.getPersonNumber());
        String name = response.getForename() + " " + response.getSurname();
        String address = response.getAddress() + ", " + response.getPostCode();
        Date dateOfBirth = DateFormatter.toDate(response.getDOB());
        String nhsNumber = String.valueOf(response.getNHSNumber());

        PatientDetails details = new PatientDetails();

        details.setId(sourceId);
        details.setName(name);
        details.setAddress(address);
        details.setDateOfBirth(dateOfBirth);
        details.setGender(response.getGender());
        details.setNhsNumber(nhsNumber);
        details.setPasNumber(response.getPersonNumber());

        return details;
    }
}
