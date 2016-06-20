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

import org.hscieripple.patient.datasources.*;
import org.hscieripple.patient.datasources.model.DataSourceStatus;
import org.hscieripple.patient.notification.NotificationService;
import org.hscieripple.patient.notification.NotificationServiceImpl;
import org.hscieripple.patient.notification.model.Notification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class HSCIEDataSourcesSearchTest {

    @InjectMocks
    HSCIEDataSourcesSearch hscieDataSourcesSearch;

    @Spy
    NotificationService notificationService = new NotificationServiceImpl();

    @Mock
    DataSourcesServiceSoap dataSourcesService;

    private Collection<Notification> notifications;

    private List<DataSourceStatus> dataSourceStatuses;

    private DataSourceStatusResponse response;
    private DataSourceStatusResponse changedResponse;

    @Before
    public void setUp() {
        // Initial population
        DSStatusResultRow resultRow1 = new DSStatusResultRow();
        resultRow1.setDataSourceName("Test One");
        resultRow1.setDataSourceStatus("Error");

        DSStatusResultRow resultRow2 = new DSStatusResultRow();
        resultRow2.setDataSourceName("Test Two");
        resultRow2.setDataSourceStatus("Error");

        DSStatusResultRow resultRow3 = new DSStatusResultRow();
        resultRow3.setDataSourceName("Test Three");
        resultRow3.setDataSourceStatus("Disabled");

        List<DSStatusResultRow> resultRows = new ArrayList<>();
        resultRows.add(resultRow1);
        resultRows.add(resultRow2);
        resultRows.add(resultRow3);

        ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow resultRowList = new ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow();
        Field resultRowsField = ReflectionUtils.findField(ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow.class, "dsStatusResultRow");
        resultRowsField.setAccessible(true);
        ReflectionUtils.setField(resultRowsField, resultRowList, resultRows);

        response = new DataSourceStatusResponse();
        response.setResultsSet(resultRowList);
        response.setStatusCode("OK");

        // Changed result rows
        DSStatusResultRow changedResultRow1 = new DSStatusResultRow();
        changedResultRow1.setDataSourceName("Test One");
        changedResultRow1.setDataSourceStatus("OK");

        DSStatusResultRow changedResultRow2 = new DSStatusResultRow();
        changedResultRow2.setDataSourceName("Test Two");
        changedResultRow2.setDataSourceStatus("OK");

        DSStatusResultRow changedResultRow3 = new DSStatusResultRow();
        changedResultRow3.setDataSourceName("Test Three");
        changedResultRow3.setDataSourceStatus("OK");

        List<DSStatusResultRow> changedResultRows = new ArrayList<>();
        changedResultRows.add(changedResultRow1);
        changedResultRows.add(changedResultRow2);
        changedResultRows.add(changedResultRow3);

        ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow changedResultRowList = new ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow();
        Field changedResultRowsField = ReflectionUtils.findField(ArrayOfDSStatusResultRowPairOfResultsSetKeyDSStatusResultRow.class, "dsStatusResultRow");
        resultRowsField.setAccessible(true);
        ReflectionUtils.setField(changedResultRowsField, changedResultRowList, changedResultRows);

        changedResponse = new DataSourceStatusResponse();
        changedResponse.setResultsSet(changedResultRowList);
        changedResponse.setStatusCode("OK");
    }

    @Test
    public void shouldReturnNotOKNotificationsWhenNotificationsFirstInitialized() {
        when(dataSourcesService.findStatusDSBO()).thenReturn(response);

        Collection<Notification> returnedNotifications = hscieDataSourcesSearch.getNotifications();
        assertEquals(3, returnedNotifications.size());
        for(Notification notification : returnedNotifications) {
            assertFalse(notification.getAvailable());
        }
    }

    @Test
    public void shouldReturnChangedNotificationsWhenNotificationsUpdated() {
        // This will initialize the notifications with currently unavailable sources
        when(dataSourcesService.findStatusDSBO()).thenReturn(response);
        hscieDataSourcesSearch.getNotifications();

        // This will then add the changed sources to the notifications
        when(dataSourcesService.findStatusDSBO()).thenReturn(changedResponse);
        Collection<Notification> returnedNotifications = hscieDataSourcesSearch.getNotifications();

        assertEquals(3, returnedNotifications.size());

        for(Notification notification : returnedNotifications) {
            assertTrue(notification.getAvailable());
        }
    }

    @Test
    public void shouldRemoveNotificationFromNotifications() {
        when(dataSourcesService.findStatusDSBO()).thenReturn(response);
        Collection<Notification> beforeRemoveNotifications = hscieDataSourcesSearch.getNotifications();
        Notification[] notificationsArray = beforeRemoveNotifications.toArray(new Notification[beforeRemoveNotifications.size()]);

        Notification notificationToBeRemoved = notificationsArray[0];
        hscieDataSourcesSearch.remove(notificationToBeRemoved.getSource());
        Collection<Notification> afterRemoveNotifications = hscieDataSourcesSearch.getNotifications();

        assertEquals(3, notificationsArray.length);
        assertEquals(2, afterRemoveNotifications.size());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRemoveNotificationFromNotifications() {
        when(dataSourcesService.findStatusDSBO()).thenReturn(response);

        hscieDataSourcesSearch.remove(null);
    }
}
