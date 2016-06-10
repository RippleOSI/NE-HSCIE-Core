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

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.contacts.ContactsDetailsResponse;
import org.hscieripple.patient.contacts.ContactsSummaryResponse;
import org.hscieripple.patient.contacts.ContactsServiceSoap;
import org.hscieripple.patient.contacts.PairOfContactsListKeyContactsSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.patient.contacts.model.HSCIEContactDetails;
import org.hscieripple.patient.contacts.model.HSCIEContactSummary;
import org.hscieripple.patient.contacts.search.HSCIEContactSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEContactsSearch extends AbstractHSCIEService implements HSCIEContactSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEContactsSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private ContactsServiceSoap contactsService;

    @Override
    public List<HSCIEContactSummary> findAllContacts(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<HSCIEContactSummary> contacts = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<HSCIEContactSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            contacts.addAll(results);
        }

        return contacts;
    }

    @Override
    public HSCIEContactDetails findContact(String patientId, String contactId, String source) {
        ContactsDetailsResponse response = new ContactsDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = contactsService.findContactsDetailsBO(nhsNumber, contactId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new HSCIEContactDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new ContactsDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<HSCIEContactSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfContactsListKeyContactsSummaryResultRow> results = new ArrayList<>();

        try {
            ContactsSummaryResponse response = contactsService.findContactsSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getContactsList().getContactsSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new ContactsResponseToContactsSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(ContactsSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(ContactsDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
