package org.hscieripple.audit.store;

import javax.annotation.PostConstruct;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.model.AuditEntity;
import org.hscieripple.audit.repo.AuditRepository;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.RepoSourceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DbAuditStore implements AuditStore {

    @Value("${legacy.datasource.priority:900}")
    private int priority;
    
    @Autowired
    private AuditRepository auditRepository;
    
    private AuditDetailsToAuditEntityTransformer transformer;
    
    @PostConstruct
    public void init() {
    	transformer = new AuditDetailsToAuditEntityTransformer();
    }
	
	@Override
	public RepoSourceType getSource() {
		return RepoSourceTypes.LEGACY;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void create(String patientId, AuditDetails auditDetails) {
		AuditEntity auditEntity = transformer.transform(auditDetails);
		
		if(auditEntity != null) {
			auditRepository.save(auditEntity);
		}
		else {
			// TODO - log and throw?
		}	
	}

}
