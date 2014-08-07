package com.visa.gpd.jersey.server.spring;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

/**
 * User: youngl
 * Date: 7/31/14
 */
//@Provider
public class Spring4LifecycleListener implements ContainerLifecycleListener {

  @Inject
  private ApplicationContext ctx;

  @Override
  public void onStartup(Container container) {
  }

  @Override
  public void onReload(Container container) {
    if(ctx instanceof ConfigurableApplicationContext) {
      ((ConfigurableApplicationContext)ctx).refresh();
    }
  }

  @Override
  public void onShutdown(Container container) {
    if(ctx instanceof ConfigurableApplicationContext) {
      ((ConfigurableApplicationContext)ctx).close();
    }
  }
}