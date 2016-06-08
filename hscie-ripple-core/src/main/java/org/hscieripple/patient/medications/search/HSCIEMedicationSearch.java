/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the MedicationSTFT License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.MedicationSTFT.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.patient.medications.search;

import java.util.List;

import org.rippleosi.common.repo.Repository;
import org.hscieripple.patient.medications.model.HSCIEMedicationDetails;
import org.hscieripple.patient.medications.model.HSCIEMedicationSummary;
import org.hscieripple.patient.datasources.model.DataSourceSummary;

public interface HSCIEMedicationSearch extends Repository {

    List<HSCIEMedicationSummary> findAllMedications(String patientId, List<DataSourceSummary> datasourceSummaries);

    HSCIEMedicationDetails findMedication(String patientId, String medicationId, String sourceId);
}
