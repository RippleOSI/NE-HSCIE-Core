package org.hscieripple.patient.consent.store;

import org.rippleosi.common.repo.AbstractRepositoryFactory;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class DefaultConsentStoreFactory extends AbstractRepositoryFactory<ConsentStore> implements ConsentStoreFactory {

    @Override
    protected ConsentStore defaultRepository() {
        return new NotConfiguredConsentStore();
    }

    @Override
    protected Class<ConsentStore> repositoryClass() {
        return ConsentStore.class;
    }
}
