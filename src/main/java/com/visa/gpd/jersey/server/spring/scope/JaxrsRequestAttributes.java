package com.visa.gpd.jersey.server.spring.scope;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.AbstractRequestAttributes;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * User: youngl
 * Date: 7/31/14
 */
class JaxrsRequestAttributes extends AbstractRequestAttributes {

  private final ContainerRequestContext requestContext;

  /**
   * Create a new instance.
   * @param requestContext JAX-RS container request context
   */
  JaxrsRequestAttributes(ContainerRequestContext requestContext) {
    this.requestContext = requestContext;
  }

  @Override
  protected void updateAccessedSessionAttributes() {
    // sessions not supported
  }

  @Override
  public Object getAttribute(String name, int scope) {
    return requestContext.getProperty(name);
  }

  @Override
  public void setAttribute(String name, Object value, int scope) {
    requestContext.setProperty(name, value);
  }

  @Override
  public void removeAttribute(String name, int scope) {
    requestContext.removeProperty(name);
  }

  @Override
  public String[] getAttributeNames(int scope) {
    if (!isRequestActive()) {
      throw new IllegalStateException("not in request scope: " + scope);
    }
    return StringUtils.toStringArray(requestContext.getPropertyNames());
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback, int scope) {
    registerRequestDestructionCallback(name, callback);
  }

  @Override
  public Object resolveReference(String key) {
    if (REFERENCE_REQUEST.equals(key)) {
      return requestContext;
    }
    return null;
  }

  @Override
  public String getSessionId() {
    return null;
  }

  @Override
  public Object getSessionMutex() {
    return null;
  }

}
