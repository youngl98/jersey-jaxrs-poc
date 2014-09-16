package com.visa.gpd.jersey.server.spring;

import com.visa.gpd.data.jpa.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * User: youngl
 * Date: 7/31/14
 *
 * A convenience class that helps the user avoid having to configure Spring ContextLoaderListener and
 * RequestContextListener in web.xml. Alternatively the user can configure these in web application web.xml.
 */
public class Spring4WebApplicationInitializer implements WebApplicationInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Spring4WebApplicationInitializer.class
                                                                   .getName());

  @Override
  public void onStartup(ServletContext sc) throws ServletException {
    // Create the 'root' Spring application context
    LOGGER.info("registering context loader listener");
    AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();

    rootContext.setConfigLocations(new String[] {
        Spring4Configuration.class.getPackage().getName(),
        ApplicationConfig.class.getPackage().getName()
    });
    // Manage the lifecycle of the root application context
    sc.addListener(new ContextLoaderListener(rootContext));

    sc.addListener(new RequestContextListener());
  }
}
