/*
 * Copyright 2016 Ripple OSI
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

package org.hscieripple.patient.consent.store;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.InOnly;
import org.hscieripple.patient.consent.model.ConsentDetails;
import org.rippleosi.common.repo.Repository;

/**
 */
@InOnly
public interface ConsentStore extends Repository {

    void create(@Header("patientId") String patientId, @Body ConsentDetails consentDetails);

    void update(@Header("patientId") String patientId, @Body ConsentDetails consentDetails);
}
