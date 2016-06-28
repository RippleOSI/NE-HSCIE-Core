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

package org.hscieripple.patient.transfers.search;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.transfers.TransferOfCareDetailsResponse;
import org.hscieripple.patient.transfers.model.HSCIETransferOfCareDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class TransferOfCareDetailsResponseToDetailsTransformer implements Transformer<TransferOfCareDetailsResponse, HSCIETransferOfCareDetails> {

    @Override
    public HSCIETransferOfCareDetails transform(TransferOfCareDetailsResponse response) {
    	 
    	Date dateOfTransfer = HSCIEDateFormatter.toDate(response.getDateOfTransfer(),response.getDataSourceName());
    	

    	    	
    	HSCIETransferOfCareDetails details = new HSCIETransferOfCareDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
        details.setReasonForContact(response.getReasonForContact());
        details.setClinicalSummary(response.getClinicalSummary());
        details.setSiteFrom(response.getSiteFrom());
        details.setSiteTo(response.getSiteTo());
        details.setDateOfTransfer(dateOfTransfer);

        return details;
    }
}
