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

import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.orders.OrdersDetailsResponse;
import org.hscieripple.patient.orders.OrdersSummaryResponse;
import org.hscieripple.patient.orders.OrderServiceSoap;
import org.hscieripple.patient.orders.PairOfOrdersListKeyOrdersSummaryResultRow;
import org.hscieripple.common.service.AbstractHSCIEService; 
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.rippleosi.patient.laborders.model.LabOrderDetails;
import org.rippleosi.patient.laborders.model.LabOrderSummary;
import org.hscieripple.patient.orders.search.HSCIEOrderSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEOrdersSearch extends AbstractHSCIEService implements HSCIEOrderSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEOrdersSearch.class);
    
    private static final String OK = "OK";

    @Autowired
    private OrderServiceSoap ordersService;

    @Override
    public List<LabOrderSummary> findAllOrders(String patientId, List<DataSourceSummary> datasourceSummaries) {
        List<LabOrderSummary> orders = new ArrayList<>();

        Long nhsNumber = convertPatientIdToLong(patientId);

        for (DataSourceSummary summary : datasourceSummaries) {
            List<LabOrderSummary> results = makeSummaryCall(nhsNumber, summary.getSourceId());

            orders.addAll(results);
        }

        return orders;
    }

    @Override
    public LabOrderDetails findOrder(String patientId, String orderId, String source) {
        OrdersDetailsResponse response = new OrdersDetailsResponse();

        Long nhsNumber = convertPatientIdToLong(patientId);

        try {
            response = ordersService.findOrdersDetailsBO(nhsNumber, orderId, source);

            if (!isSuccessfulDetailsResponse(response)) {
                return new LabOrderDetails();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return new OrdersDetailsResponseToDetailsTransformer().transform(response);
    }

    private List<LabOrderSummary> makeSummaryCall(Long nhsNumber, String source) {
        List<PairOfOrdersListKeyOrdersSummaryResultRow> results = new ArrayList<>();

        try {
            OrdersSummaryResponse response = ordersService.findOrdersSummariesBO(nhsNumber, source);

            if (isSuccessfulSummaryResponse(response)) {
                results = response.getOrdersList().getOrdersSummaryResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(results, new OrdersResponseToOrdersSummaryTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulSummaryResponse(OrdersSummaryResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }

    private boolean isSuccessfulDetailsResponse(OrdersDetailsResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode()); 
    }
}
