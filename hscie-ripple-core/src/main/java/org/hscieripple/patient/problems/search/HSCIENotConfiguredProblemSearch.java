/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.problems.search;

import java.util.List;

import org.rippleosi.common.exception.ConfigurationException;
import org.rippleosi.common.types.RepoSourceTypes;
import org.rippleosi.patient.problems.model.ProblemHeadline;
import org.rippleosi.common.types.RepoSourceType;
import org.hscieripple.patient.problems.model.HSCIEProblemDetails;
import org.hscieripple.patient.problems.model.HSCIEProblemSummary;
import org.hscieripple.patient.contacts.search.HSCIEContactSearch;
import org.hscieripple.patient.datasources.model.DataSourceSummary;

/**
 */
public class HSCIENotConfiguredProblemSearch implements HSCIEProblemSearch {

	@Override
    public RepoSourceType getSource() {
        return RepoSourceTypes.NONE;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }



    @Override
    public List<HSCIEProblemSummary> findAllProblems(String patientId, List<DataSourceSummary> datasourceSummaries) {
        throw ConfigurationException.unimplementedTransaction(HSCIEProblemSearch.class);
    }
    
    @Override
    public List<ProblemHeadline> findAllProblemHeadlines(String patientId, List<DataSourceSummary> datasourceSummaries) {
        throw ConfigurationException.unimplementedTransaction(HSCIEProblemSearch.class);
    }

    @Override
    public HSCIEProblemDetails findProblem(String patientId, String problemId, String source) {
        throw ConfigurationException.unimplementedTransaction(HSCIEProblemSearch.class);
    }
    
    
}
