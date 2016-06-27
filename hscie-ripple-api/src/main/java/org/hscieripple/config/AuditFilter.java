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
package org.hscieripple.config;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hscieripple.audit.model.AuditedAction;
import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.store.AuditStore;
import org.hscieripple.audit.store.AuditStoreFactory;
import org.rippleosi.common.types.RepoSourceTypes;
import org.rippleosi.users.model.UserDetails;
import org.rippleosi.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 */
public class AuditFilter extends OncePerRequestFilter {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String targetResource = request.getRequestURI();
		Long targetNhsNumber = parseNhsNumber(targetResource);
		
		if(targetNhsNumber != null) {
			
			UserService userService = getUserService(request);
			UserDetails userDetails = userService.findUserDetails(request, response);
			
			if(userDetails != null) {
				String userName = userDetails.getUsername();
			
				AuditDetails auditDetails = new AuditDetails();
				auditDetails.setRequesterUsername(userName);
				auditDetails.setTargetResource(targetResource);
				auditDetails.setTargetNhsNumber(targetNhsNumber);
				auditDetails.setRequestDateTime(Calendar.getInstance().getTime());
				auditDetails.setAction(getAction(request.getMethod()));
								
		        AuditStoreFactory auditStoreFactory = getAuditStoreFactory(request);
				AuditStore auditStore = auditStoreFactory.select(RepoSourceTypes.LEGACY);
		        auditStore.create(auditDetails);
			}
			else {
				LOGGER.warn(String.format("Unable to log request (resource - %s) as no user details could be found", targetResource));
			}
		}

		// must be last step
		filterChain.doFilter(request, response);
	}
	
	private AuditedAction getAction(String httpVerb) {
		AuditedAction action = null;
		
		switch(httpVerb) {
			case("POST"): action = AuditedAction.CREATE; break; 
			case("PUT"): action = AuditedAction.UPDATE; break; 
			case("GET"): action = AuditedAction.READ; break; 
			default: action = AuditedAction.NULL; break;
		}
		
		return action;
	}
	
	private Long parseNhsNumber(String targetResource) {
		Long nhsNumber = null;
		
		Pattern nhsNumberPattern = Pattern.compile(".*/patients/(\\d+).*");
		Matcher matcher = nhsNumberPattern.matcher(targetResource);
		
		if(matcher.matches()) {
			nhsNumber = Long.parseLong(matcher.group(1));
		}
		
		return nhsNumber;
	}
	
	private AuditStoreFactory getAuditStoreFactory(HttpServletRequest request) {
		WebApplicationContext springContext = RequestContextUtils.findWebApplicationContext(request);
		
		return springContext.getBean(AuditStoreFactory.class);
	}
	
	private UserService getUserService(HttpServletRequest request) {
		WebApplicationContext springContext = RequestContextUtils.findWebApplicationContext(request);
		
		return springContext.getBean(UserService.class);
	}
}	