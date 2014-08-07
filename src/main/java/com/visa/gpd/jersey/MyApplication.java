package com.visa.gpd.jersey;

import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.visa.gpd.data.jpa.ApplicationConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * User: youngl
 * Date: 7/30/14
 */

// Descriptor-less deployment of REST-Ful services are possible whe
// using servlet 3.x containers.
// @ApplicationPath annotation defines the base application URI for
// all JAX-RS resources configured in the application

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyApplication.class.getName());

  public MyApplication() {
    super(
        JacksonFeatures.class
    );
    LOGGER.info("=====================loading MyApplication");
    // Register all resources present under the package.
    packages(getClass().getPackage().getName(),
             ApplicationConfig.class.getPackage().getName());
  }
}
