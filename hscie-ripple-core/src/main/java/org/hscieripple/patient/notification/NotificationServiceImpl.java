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
package org.hscieripple.patient.notification;

import org.hscieripple.common.exception.NotificationException;
import org.hscieripple.patient.notification.model.Notification;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class NotificationServiceImpl implements NotificationService {

    private Map<String, Notification> SOURCES_STATUS_MAP;

    @Override
    public void addNotifications(final Collection<Notification> notifications) {
        if (SOURCES_STATUS_MAP == null)
        {
            SOURCES_STATUS_MAP = new ConcurrentHashMap<>();
        }

        for (Notification notification : notifications)
        {
            if(notification.getSource() != null) {
                SOURCES_STATUS_MAP.put(notification.getSource(), notification);
            }
        }
    }

    public Collection<Notification> getNotifications() {
        if(!isNotificationsInstantiated()) {
            throw new NotificationException("No notifications have been added.");
        }

        return SOURCES_STATUS_MAP.values();
    }

    public void removeNotification(final String sourceId) {
        if(sourceId == null) {
            throw new NotificationException("A value must be entered for the source Id.");
        }

        if(!isNotificationsInstantiated()) {
            throw new NotificationException("No notifications have been added.");
        }

        SOURCES_STATUS_MAP.remove(sourceId);
    }

    public boolean isNotificationsInstantiated() {
        return SOURCES_STATUS_MAP != null;
    }
}
