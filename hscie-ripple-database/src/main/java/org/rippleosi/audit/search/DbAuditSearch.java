/**
 * 
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
import org.rippleosi.search.common.model.PageableTableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

/**
 * @author WeatherillW
 *
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
	public List<AuditSummary> findAllAudits(PageableTableQuery tableQuery) {
        List<AuditEntity> audits = auditRepository.findAll(new Sort(SORT_PROPERTY));

        return CollectionUtils.collect(audits, toAuditSummaryTransformer, new ArrayList<>());
	}

	/* (non-Javadoc)
	 * @see org.hscieripple.audit.search.AuditSearch#findAuditsByPatientAndUser(long, java.lang.String)
	 */
	@Override
	public List<AuditDetails> findAuditsByPatientAndUser(long patientNhsNumber, String requesterUsername, PageableTableQuery tableQuery) {
		QAuditEntity blueprint = QAuditEntity.auditEntity;
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(blueprint.targetNhsNumber.eq(Long.toString(patientNhsNumber)));
		predicate.and(blueprint.requesterUsername.eq(requesterUsername));
		
		Page<AuditEntity> audits = auditRepository.findAll(predicate, generatePageRequest(tableQuery));
		
		return CollectionUtils.collect(audits, toAuditDetailsTransformer, new ArrayList<>());
	}
	
    private PageRequest generatePageRequest(PageableTableQuery tableQuery) {
        // determine page number (zero indexed) and sort direction
        Integer pageNumber = Integer.valueOf(tableQuery.getPageNumber()) - 1;
        Direction sortDirection = Direction.fromString(tableQuery.getOrderType());

        // create the request for a page (sorted by target NHS number)
        return new PageRequest(pageNumber, 15, sortDirection, SORT_PROPERTY);
    }

}
