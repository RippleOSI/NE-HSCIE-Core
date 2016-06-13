package org.hscieripple.audit.store;

import org.apache.camel.Body;
import org.apache.camel.InOnly;
import org.hscieripple.audit.model.AuditDetails;
import org.rippleosi.common.repo.Repository;

/**
 * An AuditStore provides a means for an {@link AuditDetails} record to be persisted.
 * Note that this interface only permits creates. Updates are not allowed. Once an
 * audit record has been created it is considered immutable.
 * 
 * @author WeatherillW
 *
 */
@InOnly
public interface AuditStore  extends Repository {

    void create(@Body AuditDetails auditDetails);

}
