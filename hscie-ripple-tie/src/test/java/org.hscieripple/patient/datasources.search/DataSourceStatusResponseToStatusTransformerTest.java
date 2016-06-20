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

import org.hscieripple.patient.datasources.DSStatusResultRow;
import org.hscieripple.patient.datasources.model.DataSourceStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 */
public class DataSourceStatusResponseToStatusTransformerTest {

    private DSStatusResultRow dsStatusResultRow;
    private DataSourceStatusResponseToStatusTransformer transformer;

    @Before
    public void setUp() {
        dsStatusResultRow = new DSStatusResultRow();
        dsStatusResultRow.setDataSourceName("Test");
        dsStatusResultRow.setDataSourceStatus("OK");

        transformer = new DataSourceStatusResponseToStatusTransformer();
    }

    @Test
    public void mustReturnDataSourceStatusObject() {
        final DataSourceStatus sourceStatus = transformer.transform(dsStatusResultRow);

        assertNotNull("DataSourceStatus object must not be null", sourceStatus);
    }

    @Test
    public void mustReturnValidDataSourceStatusObjectWithValidInputs() {
        final DataSourceStatus sourceStatus = transformer.transform(dsStatusResultRow);

        assertEquals("DataSourceStatus 'source' field was not set.", dsStatusResultRow.getDataSourceName(), sourceStatus.getSource());
        assertEquals("DataSourceStatus 'status' field was not set.", dsStatusResultRow.getDataSourceStatus(), sourceStatus.getStatus());
    }
}
