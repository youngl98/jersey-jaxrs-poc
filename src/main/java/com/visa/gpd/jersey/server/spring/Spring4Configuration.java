package com.visa.gpd.jersey.server.spring;

import com.visa.gpd.data.jpa.ApplicationConfig;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestScope;

import java.util.HashMap;
import java.util.Map;

/**
 * User: youngl
 * Date: 7/31/14
 */
@Configuration
public class Spring4Configuration {

  @Bean
  public static CustomScopeConfigurer customConfigurer () {
    CustomScopeConfigurer configurer = new CustomScopeConfigurer();

    Map<String, Object> requestScope = new HashMap<String, Object>();
    requestScope.put("request", new RequestScope());
    configurer.setScopes(requestScope);
    return configurer;
  }
}
