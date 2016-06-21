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
package org.hscieripple.patient.notifications.rest;

import org.hscieripple.patient.datasources.search.DataSourcesSearch;
import org.hscieripple.patient.datasources.search.DataSourcesSearchFactory;
import org.hscieripple.patient.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 */
@RestController
@RequestMapping("notifications")
public class NotificationsController {

    @Autowired
    private DataSourcesSearchFactory dataSourcesSearchFactory;

    @RequestMapping(value = "/{source}/acknowledge", method = RequestMethod.POST)
    public void acknowledge(@PathVariable("source") String source) {
        final DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);

        dataSourcesSearch.remove(source);
    }

    @RequestMapping(value = "/sources", method = RequestMethod.GET)
    public Collection<Notification> findAllSourceNotifications() {

        final DataSourcesSearch dataSourcesSearch = dataSourcesSearchFactory.select(null);

        return dataSourcesSearch.getNotifications();
    }
}
