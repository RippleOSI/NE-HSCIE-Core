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

import org.apache.camel.Consume;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.consent.model.ConsentDetails;
import org.hscieripple.patient.consent.store.ConsentStore;
import org.rippleosi.common.exception.UpdateFailedException;
import org.rippleosi.common.util.DateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.SOAPFaultException;

/**
 */
@Service
public class HSCIEConsentStore extends AbstractHSCIEService implements ConsentStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(HSCIEConsentSearch.class);

    private static final String OK = "OK";

    @Autowired
    private ConsentInsertServiceSoap consentInsertService;

    @Autowired
    private ConsentUpdateServiceSoap consentUpdateService;

    @Override
    @Consume(uri = "activemq:topic:VirtualTopic.HSCIE.Consent.Create")
    public void create(final String patientId, final ConsentDetails details) {

        final Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            final ConsentInsertResponse response = consentInsertService.findConsentInsertBO(nhsNumber,
                                                                                            details.isOptIn(),
                                                                                            details.getReason(),
                                                                                            DateFormatter.toString(details.getDateCreated()),
                                                                                            details.getAuthor());

            if (!isSuccessfulInsertResponse(response)) {
                throw new UpdateFailedException("Could not create consent for patient " + patientId);
            }
        }
        catch (final SOAPFaultException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    @Consume(uri = "activemq:topic:VirtualTopic.HSCIE.Consent.Update")
    public void update(final String patientId, final ConsentDetails details) {
        final Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            final ConsentUpdateResponse response = consentUpdateService.findConsentUpdateBO(nhsNumber,
                                                                                            details.getReason(),
                                                                                            details.getSourceId());

            if (!isSuccessfulUpdateResponse(response)) {
                throw new UpdateFailedException("Could not update consent " + details.getSourceId() + " for patient " + patientId);
            }
        }
        catch (final SOAPFaultException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private boolean isSuccessfulInsertResponse(final ConsentInsertResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }

    private boolean isSuccessfulUpdateResponse(final ConsentUpdateResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }
}
