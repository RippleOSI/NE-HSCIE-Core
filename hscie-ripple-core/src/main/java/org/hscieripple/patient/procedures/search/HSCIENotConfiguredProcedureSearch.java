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
package org.hscieripple.patient.procedures.search;

import java.util.List;

import org.rippleosi.common.exception.ConfigurationException;
import org.rippleosi.common.types.RepoSourceTypes; 
import org.rippleosi.common.types.RepoSourceType;
import org.hscieripple.patient.procedures.model.HSCIEProcedureDetails;
import org.hscieripple.patient.procedures.model.HSCIEProcedureSummary;
import org.hscieripple.patient.datasources.model.DataSourceSummary;

public class HSCIENotConfiguredProcedureSearch implements HSCIEProcedureSearch {

	@Override
    public RepoSourceType getSource() {
        return RepoSourceTypes.NONE;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public List<HSCIEProcedureSummary> findAllProcedures(String patientId, List<DataSourceSummary> datasourceSummaries) {
        throw ConfigurationException.unimplementedTransaction(HSCIEProcedureSearch.class);
    }

    @Override
    public HSCIEProcedureDetails findProcedure(String patientId, String procedureId, String sourceId) {
        throw ConfigurationException.unimplementedTransaction(HSCIEProcedureSearch.class);
    }
}
