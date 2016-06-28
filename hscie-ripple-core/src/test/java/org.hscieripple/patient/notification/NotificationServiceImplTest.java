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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    private NotificationServiceImpl notificationService;

    @Before
    public void setUp() throws Exception {
        notificationService = new NotificationServiceImpl();
    }

    @Test()
    public void shouldAddToNotificationsWhenAddingNotification() {
        final Notification notification = new Notification();
        notification.setSource("Test");

        final Collection<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        notificationService.addNotifications(notifications);
        assertEquals(1, notificationService.getNotifications().size());
    }

    @Test(expected = NotificationException.class)
    public void shouldReturnRuntimeExceptionWhenAttemptingToReturnNullNotifications() {
        notificationService.getNotifications();
    }

    @Test(expected = NotificationException.class)
    public void shouldReturnRuntimeExceptionWhenAttemptingToRemoveNotificationWithNullArgument() {
        notificationService.removeNotification(null);
    }

    @Test
    public void shouldReturnTrueWhenInstantiated() {
        Collection<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());

        notificationService.addNotifications(notifications);

        assertEquals(true, notificationService.isNotificationsInstantiated());
    }

    @Test
    public void shouldReturnFalseWhenUninstantiated() {
        assertEquals(false, notificationService.isNotificationsInstantiated());
    }
}
