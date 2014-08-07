package com.visa.gpd.jersey.server.spring.scope;

import org.glassfish.hk2.api.ServiceLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * User: youngl
 * Date: 7/31/14
 */
@Provider
@PreMatching
public class RequestContextFilter implements ContainerRequestFilter, ContainerResponseFilter {

  private static final String REQUEST_ATTRIBUTES_PROPERTY = RequestContextFilter.class.getName() + ".REQUEST_ATTRIBUTES";

  private final SpringAttributeController attributeController;

  private static final SpringAttributeController EMPTY_ATTRIBUTE_CONTROLLER = new SpringAttributeController() {
    @Override
    public void setAttributes(ContainerRequestContext requestContext) {
    }

    @Override
    public void resetAttributes(ContainerRequestContext requestContext) {
    }
  };

  private static interface SpringAttributeController {
    public void setAttributes(final ContainerRequestContext requestContext);
    public void resetAttributes(final ContainerRequestContext requestContext);
  }

  /**
   * Create a new request context filter instance.
   * @param locator HK2 service locator.
   */
  @Inject
  public RequestContextFilter(ServiceLocator locator) {
    ApplicationContext ctx = locator.getService(ApplicationContext.class);
    attributeController = (ctx != null) ? new SpringAttributeController() {

      @Override
      public void setAttributes(final ContainerRequestContext requestContext) {
        final JaxrsRequestAttributes attributes = new JaxrsRequestAttributes(requestContext);
        requestContext.setProperty(REQUEST_ATTRIBUTES_PROPERTY, attributes);
        RequestContextHolder.setRequestAttributes(attributes);
      }

      @Override
      public void resetAttributes(final ContainerRequestContext requestContext) {
        JaxrsRequestAttributes attributes = (JaxrsRequestAttributes)requestContext.getProperty(REQUEST_ATTRIBUTES_PROPERTY);
        RequestContextHolder.resetRequestAttributes();
        attributes.requestCompleted();
      }
    } : EMPTY_ATTRIBUTE_CONTROLLER;
  }

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {
    attributeController.setAttributes(requestContext);
  }

  @Override
  public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
    attributeController.resetAttributes(requestContext);
  }

}
