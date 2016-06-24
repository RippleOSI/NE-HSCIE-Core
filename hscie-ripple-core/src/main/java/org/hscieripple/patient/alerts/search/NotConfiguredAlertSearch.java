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
package org.hscieripple.patient.alerts.search;

import java.util.List;

import org.hscieripple.patient.alerts.model.AlertDetails;
import org.hscieripple.patient.alerts.model.AlertSummary;
import org.hscieripple.patient.datasources.model.DataSourceSummary;
import org.rippleosi.common.exception.ConfigurationException;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.RepoSourceTypes;

/**
 */
public class NotConfiguredAlertSearch implements AlertSearch {

    @Override
    public RepoSourceType getSource() {
        return RepoSourceTypes.NONE;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public List<AlertSummary> findAllAlerts(final String patientId, final List<DataSourceSummary> dataSources) {
        throw ConfigurationException.unimplementedTransaction(AlertSearch.class);
    }

    @Override
    public AlertDetails findAlert(final String patientId, final String alertId, final String subSource) {
        throw ConfigurationException.unimplementedTransaction(AlertSearch.class);
    }
}
