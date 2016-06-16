package org.hscieripple.config;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hscieripple.audit.model.AuditDetails;
import org.hscieripple.audit.store.AuditStore;
import org.hscieripple.audit.store.AuditStoreFactory;
import org.rippleosi.common.types.RepoSourceTypes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class AuditFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		System.out.println("Filter called");
		
		// TODO - temp. Replace with User service when it's available
		String tempUserName = "bob";
		
		String targetResource = request.getRequestURI();
		Long targetNhsNumber = parseNhsNumber(targetResource);
		
		if(targetNhsNumber != null) {
		
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setRequesterUsername(tempUserName);
			auditDetails.setTargetResource(targetResource);
			auditDetails.setTargetNhsNumber(targetNhsNumber);
			auditDetails.setRequestDateTime(Calendar.getInstance().getTime());
			
					
	        AuditStoreFactory auditStoreFactory = getAuditStoreFactory(request);
			AuditStore auditStore = auditStoreFactory.select(RepoSourceTypes.LEGACY);
	        auditStore.create(auditDetails);
		}
		else {
			// log the fact that we could not audit?
		}
		
		// must be last step
		filterChain.doFilter(request, response);
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
	
	// not great - couples us directly to Spring
	private AuditStoreFactory getAuditStoreFactory(HttpServletRequest request) {
		WebApplicationContext springContext = RequestContextUtils.findWebApplicationContext(request);
		
		return springContext.getBean(AuditStoreFactory.class);
	}
	
}	