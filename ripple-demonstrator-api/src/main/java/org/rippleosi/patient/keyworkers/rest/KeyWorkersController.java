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
package org.rippleosi.patient.keyworkers.rest;

import java.util.ArrayList;
import java.util.List;

import org.rippleosi.patient.keyworkers.model.KeyWorkerDetails;
import org.rippleosi.patient.keyworkers.model.KeyWorkerSummary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients/{patientId}/keyworkers")
public class KeyWorkersController {

    @RequestMapping(method = RequestMethod.GET)
    public List<KeyWorkerSummary> findAllKeyWorkers(@PathVariable("patientId") String patientId,
                                                    @RequestParam(required = false) String source) {
        KeyWorkerSummary maisy = new KeyWorkerSummary();
        maisy.setSource("Liquid Logic");
        maisy.setSourceId("1");
        maisy.setName("Maisy Cox");
        maisy.setRole("GP");
        maisy.setContactNumber("0191 123 123");

        KeyWorkerSummary joanne = new KeyWorkerSummary();
        joanne.setSource("Liquid Logic");
        joanne.setSourceId("2");
        joanne.setName("Joanne Smith");
        joanne.setRole("Social Worker");
        joanne.setContactNumber("0191 234 234");

        List<KeyWorkerSummary> summaries = new ArrayList<>();
        summaries.add(maisy);
        summaries.add(joanne);

        return summaries;
    }

    @RequestMapping(value = "/{keyWorkerId}", method = RequestMethod.GET)
    public KeyWorkerDetails findKeyWorker(@PathVariable("patientId") String patientId,
                                          @PathVariable("keyWorkerId") String keyWorkerId,
                                          @RequestParam(required = false) String source) {
        if (keyWorkerId.equals("1")) {
            KeyWorkerDetails maisy = new KeyWorkerDetails();
            maisy.setSource("Liquid Logic");
            maisy.setSourceId("1");
            maisy.setName("Maisy Cox");
            maisy.setRole("GP");
            maisy.setTeamName("Team X");
            maisy.setDepartmentDescription("Team of people from X");
            maisy.setContactNumber("0191 123 123");

            return maisy;
        }
        else if (keyWorkerId.equals("2")) {
            KeyWorkerDetails joanne = new KeyWorkerDetails();
            joanne.setSource("Liquid Logic");
            joanne.setSourceId("2");
            joanne.setName("Joanne Smith");
            joanne.setRole("Social Worker");
            joanne.setTeamName("Team Y");
            joanne.setDepartmentDescription("Team of people from Y");
            joanne.setContactNumber("0191 234 234");

            return joanne;
        }
        else {
            return new KeyWorkerDetails();
        }
    }
}
