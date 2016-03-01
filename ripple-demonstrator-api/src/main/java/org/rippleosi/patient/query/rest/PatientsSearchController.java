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
package org.rippleosi.patient.query.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.rippleosi.patient.query.search.PatientQuery;
import org.rippleosi.patient.query.search.PatientQueryFactory;
import org.rippleosi.patient.query.model.PatientQueryParams;
import org.rippleosi.patient.query.model.PatientSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/patients")
public class PatientsSearchController {

    @Autowired
    private PatientQueryFactory patientQueryFactory;

    @RequestMapping(method = RequestMethod.POST)
    public List<PatientSummary> findPatientsByQuery(@RequestParam(required = false) String source,
                                              @RequestBody PatientQueryParams queryParams) {
        PatientSummary ivor = new PatientSummary();
        ivor.setSourceId("1");
        ivor.setName("Ivor Cox");
        ivor.setAddress("55 Anne Street, Stockton-on-Tees, Durham, SL2 2II");
        ivor.setDateOfBirth(new Date());
        ivor.setGender("Male");
        ivor.setNhsNumber("9999999000");
        ivor.setSource("tie");

        PatientSummary fredericka = new PatientSummary();
        fredericka.setSourceId("2");
        fredericka.setName("Alden Cote");
        fredericka.setAddress("3115 Sit Ave, March, Cambridgeshire, PW3M 7GS");
        fredericka.setDateOfBirth(new Date());
        fredericka.setGender("Female");
        fredericka.setNhsNumber("9999999001");
        fredericka.setSource("tie");

        PatientSummary freya = new PatientSummary();
        freya.setSourceId("3");
        freya.setName("Freya Blackwell");
        freya.setAddress("2924 Pretium Av., Camborne, Cornwall, J1 3AM");
        freya.setDateOfBirth(new Date());
        freya.setGender("Female");
        freya.setNhsNumber("9999999002");
        freya.setSource("tie");

        List<PatientSummary> patients = new ArrayList<>();
        patients.add(ivor);
        patients.add(fredericka);
        patients.add(freya);

        return patients;

//        PatientQuery patientQuery = patientQueryFactory.select(source);
//
//        return patientQuery.findPatientsByQuery(queryParams);
    }
}
