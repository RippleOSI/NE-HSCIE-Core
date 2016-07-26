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

package org.hscieripple.patient.orders.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.orders.OrdersSummaryResultRow;
import org.rippleosi.patient.laborders.model.LabOrderSummary;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class OrdersResponseToOrdersSummaryTransformer implements Transformer<OrdersSummaryResultRow, LabOrderSummary> {

    @Override
    public LabOrderSummary transform(OrdersSummaryResultRow response) {
    	
    	Date dateOfOrder = HSCIEDateFormatter.toDate(response.getOrderDate(),response.getDataSourceName());
    	
    	LabOrderSummary summary = new LabOrderSummary();
    	
        summary.setSource(response.getDataSourceName());
        summary.setSourceId(response.getSourceID());
        
        summary.setName(response.getName());
        summary.setOrderDate(dateOfOrder);
        

        return summary;
    }
}
