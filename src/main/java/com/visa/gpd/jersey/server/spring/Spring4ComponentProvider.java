package com.visa.gpd.jersey.server.spring;


import java.util.Set;

import javax.servlet.ServletContext;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.spi.ComponentProvider;

import org.jvnet.hk2.spring.bridge.api.SpringBridge;
import org.jvnet.hk2.spring.bridge.api.SpringIntoHK2Bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * User: youngl
 * Date: 7/31/14
 *
 * This ComponentProvider implementation is registered with Jersey SPI extension mechanism and
 * it’s responsible for bootstrapping Jersey 2 Spring integration. It makes Jersey skip JAX-RS
 * life-cycle management for Spring components. Otherwise, Jersey would bind these classes to
 * HK2 ServiceLocator with Jersey default scope without respecting the scope declared for the Spring component.
 * This class also initializes HK2 spring-bridge and registers Spring @Autowired annotation handler
 * with HK2 ServiceLocator. When being run outside of servlet context, a custom
 * org.springframework.web.context.request.RequestScope implementation is configured to implement request scope for beans.
 */
public class Spring4ComponentProvider implements ComponentProvider {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(Spring4ComponentProvider.class.getName());
  private static final String DEFAULT_CONTEXT_CONFIG_LOCATION = "applicationContext.xml";
  private static final String PARAM_CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
  private static final String PARAM_SPRING_CONTEXT = "contextConfig";

  private volatile ServiceLocator locator;
  private volatile ApplicationContext ctx;

  @Override
  public void initialize(ServiceLocator locator) {
    this.locator = locator;

    LOGGER.info("=================================spring4 context lookup started");

    ServletContext sc = locator.getService(ServletContext.class);

    if (sc != null) {
      // servlet container
      ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
    } else {
      // non-servlet container
      ctx = createSpringContext();
    }
    if (ctx == null) {
      LOGGER.error("spring4 context lookup failed");
      return;
    }
    LOGGER.info("spring4 context loopup successful");

    // initialize HK2 spring-bridge
    SpringBridge.getSpringBridge().initializeSpringBridge(locator);
    SpringIntoHK2Bridge springBridge = locator.getService(SpringIntoHK2Bridge.class);
    springBridge.bridgeSpringBeanFactory(ctx);

    // register Spring @Autowired annotation handler with HK2 ServiceLocator
    ServiceLocatorUtilities.addOneConstant(locator, new AutowiredInjectResolver(ctx));
    ServiceLocatorUtilities.addOneConstant(locator, ctx, "SpringContext", ApplicationContext.class);
    LOGGER.info("spring4 provider initialized");
  }

  // detect JAX-RS classes that are also Spring @Components.
  // register these with HK2 ServiceLocator to manage their lifecycle using Spring.
  @Override
  public boolean bind(Class<?> component, Set<Class<?>> providerContracts) {

    if (ctx == null) {
      return false;
    }

    if (AnnotationUtils.findAnnotation(component, Component.class) != null) {
      DynamicConfiguration c = Injections.getConfiguration(locator);
      String[] beanNames = ctx.getBeanNamesForType(component);
      if (beanNames == null || beanNames.length != 1) {
        LOGGER.error("none or multitple beans avaiable " + component.getName());
        return false;
      }
      String beanName = beanNames[0];

      ServiceBindingBuilder bb = Injections.newFactoryBinder(new SpringManagedBeanFactory(ctx, locator, beanName));
      bb.to(component);
      Injections.addBinding(bb, c);
      c.commit();

      LOGGER.info("bean registered " + beanName);
      return true;
    }
    return false;
  }

  @Override
  public void done() {
  }

  private ApplicationContext createSpringContext() {
    ApplicationHandler applicationHandler = locator.getService(ApplicationHandler.class);
    ApplicationContext springContext = (ApplicationContext) applicationHandler.getConfiguration()
                                                                              .getProperty(
                                                                                  PARAM_SPRING_CONTEXT);
    if (springContext == null) {
      String contextConfigLocation =
          (String) applicationHandler.getConfiguration().getProperty(PARAM_CONTEXT_CONFIG_LOCATION);
      springContext = createXmlSpringConfiguration(contextConfigLocation);
    }
    return springContext;
  }

  private ApplicationContext createXmlSpringConfiguration(String contextConfigLocation) {
    if (contextConfigLocation == null) {
      contextConfigLocation = DEFAULT_CONTEXT_CONFIG_LOCATION;
    }
    return ctx = new ClassPathXmlApplicationContext(contextConfigLocation,
                                                    "jersey-spring-applicationContext.xml");
  }


  private static class SpringManagedBeanFactory implements Factory {
    private final ApplicationContext ctx;
    private final ServiceLocator locator;
    private final String beanName;

    private SpringManagedBeanFactory(ApplicationContext ctx, ServiceLocator locator,
                                     String beanName) {
      this.ctx = ctx;
      this.locator = locator;
      this.beanName = beanName;
    }

    @Override
    public Object provide() {
      Object bean = ctx.getBean(beanName);
      locator.inject(bean);
      return bean;
    }

    @Override
    public void dispose(Object instance) {
    }
  }
}
