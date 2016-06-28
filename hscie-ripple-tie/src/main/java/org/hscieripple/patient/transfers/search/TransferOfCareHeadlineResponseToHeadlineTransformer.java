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

package org.hscieripple.patient.transfers.search;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.rippleosi.patient.summary.model.TransferHeadline;
import org.hscieripple.common.util.HSCIEDateFormatter;
import org.hscieripple.patient.transfers.TransferOfCareHeadlineResultRow;

public class TransferOfCareHeadlineResponseToHeadlineTransformer implements Transformer<TransferOfCareHeadlineResultRow, TransferHeadline> {
    
    @Override
    public TransferHeadline transform(TransferOfCareHeadlineResultRow response) {
    	
    	Date dateOfTransfer = HSCIEDateFormatter.toDate(response.getDateOfTransfer(),response.getDataSourceName());
    	
    	
    	TransferHeadline headline = new TransferHeadline();
    	headline.setSource(response.getDataSourceName());
        headline.setSourceId(response.getSourceID());
        headline.setFrom(response.getSiteFrom());
        headline.setTo(response.getSiteTo());
        headline.setDateOfTransfer(dateOfTransfer);

        return headline;
    }
}
