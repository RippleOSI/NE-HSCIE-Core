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
package org.hscieripple.audit.search;

import java.util.List;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditSummary;
import org.rippleosi.common.repo.Repository;

/**
 */
public interface AuditSearch extends Repository {
	
	public List<AuditSummary> findAllAudits(int page);

	public List<AuditSummary> findAllAuditsByUsername(int page, String username);

	public List<AuditSummary> findAllAuditsByPatientId(int page, String patientId);

	public AuditDetails findAudit(long auditId);

	public long countAuditsByUsername(String username);

	public long countAuditsByPatientId(String patientId);

	public long countAudits();
}
