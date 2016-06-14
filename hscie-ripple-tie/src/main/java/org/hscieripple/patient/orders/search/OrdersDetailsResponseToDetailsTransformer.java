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

package org.hscieripple.patient.orders.search;

import java.util.Date;
import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.orders.OrdersDetailsResponse;
import org.hscieripple.patient.orders.model.HSCIEOrderDetails;
import org.hscieripple.common.util.HSCIEDateFormatter;

public class OrdersDetailsResponseToDetailsTransformer implements Transformer<OrdersDetailsResponse, HSCIEOrderDetails> {

    @Override
    public HSCIEOrderDetails transform(OrdersDetailsResponse response) {
    	 
    	Date orderDate = HSCIEDateFormatter.toDate(response.getOrderDate(), response.getDataSourceName());
    	    	
		HSCIEOrderDetails details = new HSCIEOrderDetails();

        details.setSource(response.getDataSourceName());
        details.setSourceId(response.getSourceID());
            
        details.setName(response.getName());
        details.setAuthor(response.getAuthor());
		details.setOrderDate(orderDate);
		
        details.setLocation(response.getLocation());
		details.setStatus(response.getStatus());

        return details;
    }
}

