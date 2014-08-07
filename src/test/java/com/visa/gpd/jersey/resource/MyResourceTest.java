package com.visa.gpd.jersey.resource;

import com.visa.gpd.data.jpa.ApplicationConfig;
import com.visa.gpd.jersey.MyApplication;
import com.visa.gpd.jersey.server.spring.Spring4Configuration;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
//import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

/**
 * User: youngl
 * Date: 7/30/14
 */
public class MyResourceTest extends JerseyTest {

  @Override
  protected DeploymentContext configureDeployment() {
    return ServletDeploymentContext.builder(new MyApplication())
                                   .contextParam(ContextLoader.CONFIG_LOCATION_PARAM, ApplicationConfig.class.getPackage().getName())
                                   .contextParam(ContextLoader.CONFIG_LOCATION_PARAM, Spring4Configuration.class.getPackage().getName())
                                   .contextParam(ContextLoader.CONTEXT_CLASS_PARAM, AnnotationConfigWebApplicationContext.class.getName())
                                   .addListener(ContextLoaderListener.class)
                                   .addListener(RequestContextListener.class)
                                   .build();
  }

  //@Override
  //protected MyApplication configure() {
  //  enable(TestProperties.LOG_TRAFFIC);
  //  enable(TestProperties.DUMP_ENTITY);
  //  return new MyApplication();
  //}

  @Override
  protected TestContainerFactory getTestContainerFactory() {
    //use in memory test container.  basically make direct method calls to the
    //resource methods
    //return new InMemoryTestContainerFactory();
    return new GrizzlyWebTestContainerFactory();
  }

  @Test
  public void testMyResource() throws Exception {
    String out = target().path("myresource").request().get(String.class);
    assertEquals("unexpected result", "Got it!", out);
  }

}
