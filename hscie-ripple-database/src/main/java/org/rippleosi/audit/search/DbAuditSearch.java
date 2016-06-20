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
package org.rippleosi.audit.search;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditSummary;
import org.hscieripple.audit.search.AuditSearch;
import org.rippleosi.audit.model.AuditEntity;
import org.rippleosi.audit.model.QAuditEntity;
import org.rippleosi.audit.repo.AuditRepository;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.RepoSourceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 */
@Service
@Transactional
public class DbAuditSearch implements AuditSearch {
	
	private static final String SORT_PROPERTY = "targetNhsNumber";

	@Value("${legacy.datasource.priority:900}")
    private int priority;
    
    @Autowired
    private AuditRepository auditRepository;   
    
    private AuditEntityToAuditSummaryTransformer toAuditSummaryTransformer;
    private AuditEntityToAuditDetailsTransformer toAuditDetailsTransformer;
    
    @PostConstruct
    public void init() {
    	toAuditSummaryTransformer = new AuditEntityToAuditSummaryTransformer();
    	toAuditDetailsTransformer = new AuditEntityToAuditDetailsTransformer();
    }	
    
	/* (non-Javadoc)
	 * @see org.rippleosi.common.repo.Repository#getSource()
	 */
	@Override
	public RepoSourceType getSource() {
		return RepoSourceTypes.LEGACY;
	}

	/* (non-Javadoc)
	 * @see org.rippleosi.common.repo.Repository#getPriority()
	 */
	@Override
	public int getPriority() {
		return priority;
	}

	/* (non-Javadoc)
	 * @see org.hscieripple.audit.search.AuditSearch#findAllAudits(java.util.List)
	 */
	@Override
	public List<AuditSummary> findAllAudits(int page) {
        Page<AuditEntity> audits = auditRepository.findAll(generatePageRequest(page));

        return CollectionUtils.collect(audits, toAuditSummaryTransformer, new ArrayList<>());
	}
	
	@Override
	public List<AuditSummary> findAllAuditsByUsername(int page, String username) {
		Page<AuditEntity> audits = auditRepository.findAll(getRequesterUserNameEqualityPredicate(username), generatePageRequest(page));
		
		return CollectionUtils.collect(audits, toAuditSummaryTransformer, new ArrayList<>());
	}

	@Override
	public List<AuditSummary> findAllAuditsByPatientId(int page, String targetNhsNumber) {
		Page<AuditEntity> audits = auditRepository.findAll(getTargetNhsNumberEqualityPredicate(targetNhsNumber), generatePageRequest(page));
		
		return CollectionUtils.collect(audits, toAuditSummaryTransformer, new ArrayList<>());
	}

	@Override
	public AuditDetails findAudit(long auditId) {
		AuditEntity auditEntity = auditRepository.findOne(auditId);
		
		return toAuditDetailsTransformer.transform(auditEntity);
	}

	@Override
	public long countAuditsByUsername(String username) {
		return auditRepository.count(getRequesterUserNameEqualityPredicate(username));
	}

	@Override
	public long countAuditsByPatientId(String targetNhsNumber) {
		return auditRepository.count(getTargetNhsNumberEqualityPredicate(targetNhsNumber));
	}

	@Override
	public long countAudits() {
		return auditRepository.count();
	}
	
	private Predicate getRequesterUserNameEqualityPredicate(String username) {
		QAuditEntity blueprint = QAuditEntity.auditEntity;
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(blueprint.requesterUsername.eq(username));
		
		return predicate;
	}
	
	private Predicate getTargetNhsNumberEqualityPredicate(String targetNhsNumber) {
		QAuditEntity blueprint = QAuditEntity.auditEntity;
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(blueprint.targetNhsNumber.eq(targetNhsNumber));
		
		return predicate;
	}

	
    private PageRequest generatePageRequest(int page) {
        // create the request for a page (sorted by target NHS number)
        return new PageRequest(page, 15, Direction.ASC, SORT_PROPERTY);
    }
}
