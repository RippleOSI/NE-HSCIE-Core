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
package org.hscieripple.patient.datasources.search;

import javax.xml.ws.soap.SOAPFaultException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.patient.datasources.*;
import org.hscieripple.patient.datasources.model.DataSourceStatus;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.hscieripple.common.service.AbstractHSCIEService;
import org.hscieripple.patient.notification.NotificationService;
import org.hscieripple.patient.notification.model.Notification;
import org.hscieripple.patient.notification.model.NotificationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSCIEDataSourcesSearch extends AbstractHSCIEService implements DataSourcesSearch {

    private static final Logger log = LoggerFactory.getLogger(HSCIEDataSourcesSearch.class);

    private static final String OK = "OK";

    private final Map<String, DataSourceStatus> SOURCES_STATUS_MAP = new ConcurrentHashMap();

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DataSourcesServiceSoap dataSourcesService;

    @Override
    public List<DataSourceSummary> findAvailableDataSources(String patientId, String dataType) {
        List<PairOfResultsSetKeyDSResultRow> dataSources = new ArrayList<>();

        try {
            DataSourceResponse response = dataSourcesService.findAvailableDSBO(dataType, convertPatientIdToLong(patientId));

            if (isSuccessfulResponse(response)) {
                dataSources = response.getResultsSet().getDSResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(dataSources, new DataSourceResponseToSummaryTransformer(), new ArrayList<>());
    }

    @Override
    public Collection<Notification> getNotifications() {
        final Collection<Notification> notifications;

        if (notificationService.isNotificationsInstantiated()) {
            notifications = getSourceStatusChangeNotifications();
        }
        else {
            notifications = getUnavailableNotifications();
        }

        notificationService.addNotifications(notifications);

        return notificationService.getNotifications();
    }

    @Override
    public void remove(final String source) {
        notificationService.removeNotification(source);
    }

    public List<DataSourceStatus> findDataSourceStatuses() {
        List<PairOfResultsSetKeyDSStatusResultRow> dataSources = new ArrayList<>();

        try {
            DataSourceStatusResponse response = dataSourcesService.findStatusDSBO();

            if (isSuccessfulResponse(response)) {
                dataSources = response.getResultsSet().getDSStatusResultRow();
            }
        }
        catch (SOAPFaultException e) {
            log.error(e.getMessage(), e);
        }

        return CollectionUtils.collect(dataSources, new DataSourceStatusResponseToStatusTransformer(), new ArrayList<>());
    }

    private List<Notification> getSourceStatusChangeNotifications() {
        final List<DataSourceStatus> dataSourceStatuses = findDataSourceStatuses();
        final Map<String, DataSourceStatus> changedStatuses = new HashMap<>();

        for(DataSourceStatus dataSource : dataSourceStatuses) {

            if (SOURCES_STATUS_MAP.containsKey(dataSource.getSource())) {

                final DataSourceStatus currentStatus = SOURCES_STATUS_MAP.get(dataSource.getSource());

                if (!currentStatus.getStatus().equals(dataSource.getStatus())) {
                    changedStatuses.put(dataSource.getSource(), dataSource);
                }
            }

            SOURCES_STATUS_MAP.put(dataSource.getSource(), dataSource);
        }

        return CollectionUtils.collect(changedStatuses.values(), new DataSourceStatusToNotificationTransformer(), new ArrayList<>());
    }

    private List<Notification> getUnavailableNotifications() {
        List<DataSourceStatus> dataSourceStatuses = findDataSourceStatuses();

        if(SOURCES_STATUS_MAP.size() == 0) {
            for(DataSourceStatus dataSource : dataSourceStatuses) {
                SOURCES_STATUS_MAP.put(dataSource.getSource(), dataSource);
            }
        }

        dataSourceStatuses = SOURCES_STATUS_MAP.values().stream()
                .filter(dataSource -> !NotificationStatus.OK.getStatus().equals(dataSource.getStatus()))
                .collect(Collectors.toList());

        return CollectionUtils.collect(dataSourceStatuses, new DataSourceStatusToNotificationTransformer(), new ArrayList<>());
    }

    private boolean isSuccessfulResponse(DataSourceResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }

    private boolean isSuccessfulResponse(DataSourceStatusResponse response) {
        return OK.equalsIgnoreCase(response.getStatusCode());
    }
}
