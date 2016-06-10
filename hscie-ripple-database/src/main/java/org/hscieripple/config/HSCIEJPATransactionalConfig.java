package org.hscieripple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.hscieripple")
public class HSCIEJPATransactionalConfig {

}

