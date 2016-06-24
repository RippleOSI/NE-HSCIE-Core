package org.hscieripple.audit.store;

import org.rippleosi.common.repo.AbstractRepositoryFactory;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class DefaultAuditStoreFactory extends AbstractRepositoryFactory<AuditStore> implements AuditStoreFactory {

    @Override
    protected AuditStore defaultRepository() {
        return new NotConfiguredAuditStore();
    }

    @Override
    protected Class<AuditStore> repositoryClass() {
        return AuditStore.class;
    }
}
