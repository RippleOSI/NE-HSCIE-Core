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
package org.rippleosi.patient.search.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.rippleosi.patient.search.model.PatientSearchParams;
import org.rippleosi.patient.summary.model.PatientSummary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/patients")
public class PatientsSearchController {

    @RequestMapping(method = RequestMethod.POST)
    public List<PatientSummary> findAllPatients(@RequestParam(required = false) String source,
                                                @RequestBody PatientSearchParams searchParams) {
        PatientSummary ivor = new PatientSummary();
        ivor.setId("1");
        ivor.setName("Ivor Cox");
        ivor.setAddress("55 Anne Street, Stockton-on-Tees, Durham, SL2 2II");
        ivor.setDateOfBirth(new Date());
        ivor.setGender("Male");
        ivor.setNhsNumber("9999999000");

        PatientSummary fredericka = new PatientSummary();
        fredericka.setId("2");
        fredericka.setName("Alden Cote");
        fredericka.setAddress("3115 Sit Ave, March, Cambridgeshire, PW3M 7GS");
        fredericka.setDateOfBirth(new Date());
        fredericka.setGender("Female");
        fredericka.setNhsNumber("9999999001");

        PatientSummary freya = new PatientSummary();
        freya.setId("3");
        freya.setName("Freya Blackwell");
        freya.setAddress("2924 Pretium Av., Camborne, Cornwall, J1 3AM");
        freya.setDateOfBirth(new Date());
        freya.setGender("Female");
        freya.setNhsNumber("9999999002");

        List<PatientSummary> patients = new ArrayList<>();
        patients.add(ivor);
        patients.add(fredericka);
        patients.add(freya);

        return patients;
    }
}
