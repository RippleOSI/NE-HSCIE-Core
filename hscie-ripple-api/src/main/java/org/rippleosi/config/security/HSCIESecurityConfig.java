package org.rippleosi.config.security;

import org.apache.commons.collections4.MapUtils;
import org.pac4j.core.authorization.Authorizer;
import org.pac4j.core.authorization.RequireAnyRoleAuthorizer;
import org.pac4j.core.config.Config;
import org.rippleosi.security.interceptor.authentication.RequiresAuthenticationInterceptor;
import org.rippleosi.security.interceptor.csrf.StartCsrfInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Component
public class HSCIESecurityConfig extends SecurityConfig {

    @Autowired
    private Config config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequiresAuthenticationInterceptor(config, "OidcClient", "governance"))
                .addPathPatterns("/**")
                .excludePathPatterns("/token", "/user", "/logout");

        config.addAuthorizer("all", new RequireAnyRoleAuthorizer("IDCR", "PHR", "IG"));
        config.addAuthorizer("clinician", new RequireAnyRoleAuthorizer("IDCR", "IG"));
        config.addAuthorizer("governance", new RequireAnyRoleAuthorizer("IDCR", "IG"));
    }
}
