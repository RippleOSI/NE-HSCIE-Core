package org.rippleosi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hscieripple.audit.store.AuditStore;
import org.hscieripple.audit.store.AuditStoreFactory;
import org.rippleosi.common.types.RepoSourceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuditFilter extends OncePerRequestFilter {

	@Autowired
	AuditStoreFactory auditStoreFactory;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		System.out.println("Filter called");
		
        AuditStore auditStore = auditStoreFactory.select(RepoSourceTypes.LEGACY);
        //auditStore.create(patientId, auditDetails);
		
		// must be last step
		filterChain.doFilter(request, response);
	}
	
}	