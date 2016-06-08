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
package org.hscieripple.patient.contact.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.contacts.ContactsDetailsResponse;
import org.hscieripple.patient.contacts.model.HSCIEContactDetails;
import org.rippleosi.common.util.HSCIEDateFormatter;

public class ContactsDetailsResponseToDetailsTransformer implements Transformer<ContactsDetailsResponse, HSCIEContactDetails> {

    @Override
    public HSCIEContactDetails transform(ContactsDetailsResponse response) {
    	 
    	Date dateCreated = HSCIEDateFormatter.toDate(response.getDateCreated(), response.getDataSourceName());
    	
    	HSCIEContactDetails details = new HSCIEContactDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
        
       
        
        details.setName(response.getName());
        details.setRelationship(response.getRelationship());
        details.setRelationshipType(response.getRelationshipType());
        details.setRelationshipCode(response.getRelationshipCode());
        details.setRelationshipTerminology(response.getRelationshipTerminology());
        details.setContactInformation(response.getContactInformation());
        details.setNextOfKin(response.isNextOfKin());
        details.setNotes(response.getNotes());
        details.setAuthor(response.getAuthor());
        details.setDateCreated(dateCreated);

        return details;
    }
}
