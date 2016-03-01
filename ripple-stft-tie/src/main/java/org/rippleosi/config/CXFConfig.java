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
package org.rippleosi.config;

import org.apache.cxf.jaxws.spring.JaxWsProxyFactoryBeanDefinitionParser.JAXWSSpringClientProxyFactoryBean;
import org.hscieripple.patient.query.PatientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.rippleosi")
public class CXFConfig {

    @Value("${hscie.tie.authentication.username:''}")
    private String username;

    @Value("${hscie.tie.authentication.password:''}")
    private String password;

    @Value("${hscie.tie.address:''}")
    private String hscieTieAddress;

    @Value("{hscie.tie.patientServiceUrl:''}")
    private String patientServiceUrl;

    @Bean
    public PatientService findPatientService() {
        return createJAXWSService(PatientService.class, patientServiceUrl);
    }

    @SuppressWarnings("unchecked")
    public <T> T createJAXWSService(Class<T> serviceClass, String serviceUrl) {
        JAXWSSpringClientProxyFactoryBean factoryBean = new JAXWSSpringClientProxyFactoryBean();

        factoryBean.setServiceClass(serviceClass);
        factoryBean.setAddress(hscieTieAddress + serviceUrl);

        factoryBean.setUsername(username);
        factoryBean.setPassword(password);

        // this bindingId needed?? would have to be parameterised in the method (one per search)
//        factoryBean.setBindingId("s0:RippleWSSoap");

        return (T) factoryBean.create();
    }
}
