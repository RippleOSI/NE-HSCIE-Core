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

import org.apache.cxf.jaxws.spring.JaxWsProxyFactoryBeanDefinitionParser.JAXWSSpringClientProxyFactoryBean;
import org.hscieripple.patient.consent.*;
import org.hscieripple.patient.datasources.DataSourcesServiceSoap;
import org.hscieripple.patient.keyworkers.KeyWorkerServiceSoap;
import org.hscieripple.patient.contacts.ContactsServiceSoap;
import org.hscieripple.patient.appointments.AppointmentServiceSoap;
import org.hscieripple.patient.referrals.ReferralServiceSoap;
import org.hscieripple.patient.problems.ProblemServiceSoap;
import org.hscieripple.patient.query.PatientServiceSoap;
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

    @Value("${hscie.tie.patientServiceUrl:''}")
    private String patientServiceUrl;

    @Value("${hscie.tie.keyWorkersServiceUrl:''}")
    private String keyWorkersServiceUrl;

    @Value("${hscie.tie.dataSourcesServiceUrl:''}")
    private String dataSourcesServiceUrl;
    
    @Value("${hscie.tie.contactsServiceUrl:''}")
    private String contactsServiceUrl;
    
    @Value("${hscie.tie.appointmentsServiceUrl:''}")
    private String appointmentsServiceUrl;
    
    @Value("${hscie.tie.referralsServiceUrl:''}")
    private String referralsServiceUrl;
    
    @Value("${hscie.tie.problemsServiceUrl:''}")
    private String problemsServiceUrl;

    @Value("${hscie.tie.consentsFindDetailsServiceUrl:''}")
    private String consentsFindDetailsServiceUrl;

    @Value("${hscie.tie.consentsFindAllServiceUrl:''}")
    private String consentsFindAllServiceUrl;

    @Value("${hscie.tie.consentsInsertUrl:''}")
    private String consentsInsertUrl;

    @Value("${hscie.tie.consentsUpdateUrl:''}")
    private String consentsUpdateUrl;

    @Bean
    public PatientServiceSoap patientService() {
        return createJAXWSService(PatientServiceSoap.class, patientServiceUrl);
    }

    @Bean
    public KeyWorkerServiceSoap keyWorkersService() {
        return createJAXWSService(KeyWorkerServiceSoap.class, keyWorkersServiceUrl);
    }

    @Bean
    public DataSourcesServiceSoap dataSourcesService() {
        return createJAXWSService(DataSourcesServiceSoap.class, dataSourcesServiceUrl);
    }
    
    @Bean
    public ContactsServiceSoap contactsService() {
      return createJAXWSService(ContactsServiceSoap.class, contactsServiceUrl);
    }
    
    @Bean
    public AppointmentServiceSoap appointmentsService() {
      return createJAXWSService(AppointmentServiceSoap.class, appointmentsServiceUrl);
    }
    
    @Bean
    public ReferralServiceSoap referralsService() {
        return createJAXWSService(ReferralServiceSoap.class, referralsServiceUrl);
    }
    
    @Bean
    public ProblemServiceSoap problemsService() {
        return createJAXWSService(ProblemServiceSoap.class, problemsServiceUrl);
    }

    @Bean
    public ConsentFindAllServiceSoap consentsFindAllService() {
        return createJAXWSService(ConsentFindAllServiceSoap.class, consentsFindAllServiceUrl);
    }

    @Bean
    public ConsentFindSingleServiceSoap consentsFindDetailsService() {
        return createJAXWSService(ConsentFindSingleServiceSoap.class, consentsFindDetailsServiceUrl);
    }

    @Bean
    public ConsentInsertServiceSoap consentsInsertService() {
        return createJAXWSService(ConsentInsertServiceSoap.class, consentsInsertUrl);
    }

    @Bean
    public ConsentUpdateServiceSoap consentsUpdateService() {
        return createJAXWSService(ConsentUpdateServiceSoap.class, consentsUpdateUrl);
    }

    public <T> T createJAXWSService(Class<T> serviceClass, String serviceUrl) {
        JAXWSSpringClientProxyFactoryBean factoryBean = new JAXWSSpringClientProxyFactoryBean();

        factoryBean.setAddress(hscieTieAddress + serviceUrl);

        factoryBean.setUsername(username);
        factoryBean.setPassword(password);

        return factoryBean.create(serviceClass);
    }
}
