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

package org.hscieripple.audit.store;

import org.apache.camel.Produce;
import org.hscieripple.audit.model.AuditDetails;
import org.rippleosi.common.types.RepoSourceType;
import org.rippleosi.common.types.RepoSourceTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author WeatherillW
 * 
 */
@Service
public class ActiveMQProxyAuditStore implements AuditStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQProxyAuditStore.class);

    @Value("${repository.config.jms:500}")
    private int priority;

    @Produce(uri = "activemq:topic:VirtualTopic.HSCIE.Audit.Create")
    private AuditStore createTopic;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public RepoSourceType getSource() {
        return RepoSourceTypes.ACTIVEMQ;
    }

    @Override
    public void create(final AuditDetails auditDetails) {
        try {
            createTopic.create(auditDetails);
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
