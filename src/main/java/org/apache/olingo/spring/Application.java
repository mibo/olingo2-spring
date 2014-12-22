/*******************************************************************************
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
******************************************************************************/

package org.apache.olingo.spring;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.apache.olingo.odata2.ref.processor.ScenarioServiceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@Configuration
public class Application {

  @Bean(name = "ODataService.svc")
  public Servlet getDispatcherServlet() {
    return new ODataServlet();
  }

  @Bean
  public ServletRegistrationBean dispatcherServletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(
        getDispatcherServlet(), "/ODataService.svc/*");
    registration
        .setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
    Map<String,String> initParams = new HashMap<String, String>();
    initParams.put(ODataServiceFactory.FACTORY_LABEL, ScenarioServiceFactory.class.getName());
    initParams.put(ODataServiceFactory.PATH_SPLIT_LABEL, "0");
    registration.setInitParameters(initParams);

    return registration;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
