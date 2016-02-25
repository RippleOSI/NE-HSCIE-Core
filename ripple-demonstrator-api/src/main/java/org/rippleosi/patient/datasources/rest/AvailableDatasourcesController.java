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
package org.rippleosi.patient.datasources.rest;

import java.util.ArrayList;
import java.util.List;

import org.rippleosi.patient.datasources.model.DatasourceSummary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients/{patientId}/datasources")
public class AvailableDatasourcesController {

    @RequestMapping(method = RequestMethod.GET)
    public List<DatasourceSummary> findAvailableDatasources(@PathVariable("patientId") String patientId,
                                                            @RequestParam(required = false) String source) {
        DatasourceSummary secondaryCare = new DatasourceSummary();
        secondaryCare.setSource("TIE");
        secondaryCare.setSourceId("Secondary Care");
        secondaryCare.setStatus("No link");

        DatasourceSummary primaryCare = new DatasourceSummary();
        primaryCare.setSource("TIE");
        primaryCare.setSourceId("Primary Care");
        primaryCare.setStatus("No link");

        DatasourceSummary mentalHealth = new DatasourceSummary();
        mentalHealth.setSource("TIE");
        mentalHealth.setSourceId("Mental Health");
        mentalHealth.setStatus("No link");

        DatasourceSummary socialCare = new DatasourceSummary();
        socialCare.setSource("TIE");
        socialCare.setSourceId("Social Care");
        socialCare.setStatus("No link");

        DatasourceSummary community = new DatasourceSummary();
        community.setSource("TIE");
        community.setSourceId("Community");
        community.setStatus("Connected");

        List<DatasourceSummary> summaries = new ArrayList<>();
        summaries.add(secondaryCare);
        summaries.add(primaryCare);
        summaries.add(mentalHealth);
        summaries.add(socialCare);
        summaries.add(community);

        return summaries;
    }

    @RequestMapping(value = "/{dataType}", method = RequestMethod.GET)
    public List<DatasourceSummary> findAvailableDatasourcesForType(@PathVariable("patientId") String patientId,
                                                                   @PathVariable("dataType") String dataType,
                                                                   @RequestParam(required = false) String source) {
        DatasourceSummary secondaryCare = new DatasourceSummary();
        secondaryCare.setSource("TIE");
        secondaryCare.setSourceId("Secondary Care");
        secondaryCare.setStatus("Connected");

        DatasourceSummary primaryCare = new DatasourceSummary();
        primaryCare.setSource("TIE");
        primaryCare.setSourceId("Primary Care");
        primaryCare.setStatus("Connected");

        DatasourceSummary mentalHealth = new DatasourceSummary();
        mentalHealth.setSource("TIE");
        mentalHealth.setSourceId("Mental Health");
        mentalHealth.setStatus("No link");

        DatasourceSummary socialCare = new DatasourceSummary();
        socialCare.setSource("TIE");
        socialCare.setSourceId("Social Care");
        socialCare.setStatus("Connected");

        DatasourceSummary community = new DatasourceSummary();
        community.setSource("TIE");
        community.setSourceId("Community");
        community.setStatus("Connected");

        List<DatasourceSummary> summaries = new ArrayList<>();
        summaries.add(secondaryCare);
        summaries.add(primaryCare);
        summaries.add(mentalHealth);
        summaries.add(socialCare);
        summaries.add(community);

        return summaries;
    }
}
