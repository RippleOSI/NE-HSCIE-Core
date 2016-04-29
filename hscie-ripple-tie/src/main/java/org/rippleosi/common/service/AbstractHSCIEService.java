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
package org.rippleosi.common.service;

import org.apache.commons.lang3.StringUtils;
import org.hscieripple.common.types.RepoSourceType;
import org.rippleosi.common.repo.Repository;
import org.rippleosi.common.types.RepoSource;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractHSCIEService implements Repository {

    @Value("${repository.config.hscie.tie:1000}")
    private int priority;

    @Override
    public RepoSource getSource() {
        return RepoSourceType.TIE;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    protected Long convertPatientIdToLong(String patientId) {
        boolean nullOrEmpty = StringUtils.isBlank(patientId);
        boolean numeric = StringUtils.isNumeric(patientId);

        return (nullOrEmpty || !numeric) ? null : Long.valueOf(patientId);
    }
}
