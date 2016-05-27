/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.consent.rest;

import java.util.List;

import org.hscieripple.patient.consent.model.ConsentDetails;
import org.hscieripple.patient.consent.model.ConsentSummary;
import org.hscieripple.patient.consent.search.ConsentSearch;
import org.hscieripple.patient.consent.search.ConsentSearchFactory;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.lookup.RepoSourceLookupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@RequestMapping("/patients/{patientId}/consents")
public class ConsentController {

    @Autowired
    private RepoSourceLookupFactory repoSourceLookup;

    @Autowired
    private ConsentSearchFactory consentSearchFactory;

    @RequestMapping(method = RequestMethod.GET)
    public List<ConsentSummary> findAllConsents(@PathVariable("patientId") String patientId,
                                                @RequestParam(required = false) String source) {

        final RepoSourceType sourceType = repoSourceLookup.lookup(source);

        ConsentSearch consentSourcesSearch = consentSearchFactory.select(sourceType);
        return consentSourcesSearch.findAllConsents(patientId, null);
    }

    @RequestMapping(value = "/{consentId}", method = RequestMethod.GET)
    public ConsentDetails findConsent(@PathVariable("patientId") String patientId,
                                     @PathVariable("consentId") String consentId,
                                     @RequestParam(required = false) String source) {

        final RepoSourceType sourceType = repoSourceLookup.lookup(source);
        ConsentSearch consentSourcesSearch = consentSearchFactory.select(sourceType);

        return consentSourcesSearch.findConsent(patientId, consentId, null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createConsent(@PathVariable("patientId") String patientId,
                              @RequestBody ConsentDetails consent,
                              @RequestParam(required = false) String source) {

        System.out.println(consent);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateConsent(@PathVariable("patientId") String patientId,
                              @RequestBody ConsentDetails consent,
                              @RequestParam(required = false) String source) {
        System.out.println(consent);
    }
}
