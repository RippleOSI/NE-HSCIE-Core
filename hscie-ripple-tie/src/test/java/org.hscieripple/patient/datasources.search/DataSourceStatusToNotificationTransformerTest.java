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

import org.hscieripple.patient.datasources.model.DataSourceStatus;
import org.hscieripple.patient.notification.model.Notification;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 */
public class DataSourceStatusToNotificationTransformerTest {

    private DataSourceStatus dataSourceStatus;
    private DataSourceStatusToNotificationTransformer transformer;

    @Before
    public void setUp() {
        dataSourceStatus = new DataSourceStatus();
        dataSourceStatus.setSource("Test");
        dataSourceStatus.setStatus("OK");

        transformer = new DataSourceStatusToNotificationTransformer();
    }

    @Test
    public void mustNotificationObject() {
        final Notification notification = transformer.transform(dataSourceStatus);

        assertNotNull("Notification object must not be null", notification);
    }

    @Test
    public void mustReturnValidNotificationObjectWithValidInputs() {
        final Notification notification = transformer.transform(dataSourceStatus);

        assertEquals("Notification 'source' field was not set.", dataSourceStatus.getSource(), notification.getSource());
        assertNotNull("Notification 'available' field was not set.", notification.getAvailable());
        assertNotNull("Notification 'timeCreated' field was not set.", notification.getTimeCreated());
        assertNotNull("Notification 'message' field was not set.", notification.getMessage());
    }

    @Test
    public void shouldReturnTrueForStatusOK() {
        final Notification notification = transformer.transform(dataSourceStatus);

        assertTrue("Notification 'available' was not set to true.", notification.getAvailable());
    }

    @Test
    public void shouldReturnFalseForOtherStatuses() {
        final DataSourceStatus unavailableSourceStatus = new DataSourceStatus();
        dataSourceStatus.setStatus("Error");

        final Notification notification = transformer.transform(unavailableSourceStatus);

        assertFalse("Notification 'available' was not set to false.", notification.getAvailable());
    }
}
