package org.hscieripple.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * @author WeatherillW
 *
 */
public class HSCIEWebInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Dynamic registration = servletContext.addFilter("org.rippleosi.config.AuditFilter", new AuditFilter());
		
		// could make this a URL mapping and use */patient/[1-9]?
		registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), false, AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME);
		//registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "patient/*");
	}
}
