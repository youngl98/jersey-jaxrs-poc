package com.visa.gpd.jersey.server.spring;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;

/**
 * User: youngl
 * Date: 7/31/14
 */
@Singleton
public class AutowiredInjectResolver implements InjectionResolver<Autowired> {

  private static final Logger
      LOGGER = LoggerFactory.getLogger(AutowiredInjectResolver.class.getName());

  private volatile ApplicationContext ctx;

  /**
   * Create a new instance.
   *
   * @param ctx Spring application context.
   */
  public AutowiredInjectResolver(ApplicationContext ctx) {
    this.ctx = ctx;
  }

  @Override
  public Object resolve(Injectee injectee, ServiceHandle<?> root) {
    AnnotatedElement parent = injectee.getParent();
    String beanName = null;
    if (parent != null) {
      Qualifier an = parent.getAnnotation(Qualifier.class);
      if (an != null) {
        beanName = an.value();
      }
    }
    return getBeanFromSpringContext(beanName, injectee);
  }

  private Object getBeanFromSpringContext(String beanName, Injectee injectee) {
    try {
      DependencyDescriptor dependencyDescriptor = createSpringDependencyDescriptor(injectee);
      Set<String> autowiredBeanNames = new HashSet<>(1);
      autowiredBeanNames.add(beanName);
      return ctx.getAutowireCapableBeanFactory().resolveDependency(dependencyDescriptor, null,
                                                                   autowiredBeanNames, null);
    } catch (NoSuchBeanDefinitionException e) {
      LOGGER.warn(e.getMessage());
      throw e;
    }
  }

  private DependencyDescriptor createSpringDependencyDescriptor(final Injectee injectee) {
    AnnotatedElement annotatedElement = injectee.getParent();
    if (annotatedElement.getClass().isAssignableFrom(Field.class)) {
      return new DependencyDescriptor((Field) annotatedElement,
                                      !injectee.isOptional());
    } else {
      return new DependencyDescriptor(
          new MethodParameter((Constructor) annotatedElement, injectee.getPosition()), !injectee.isOptional());
    }
  }

  @Override
  public boolean isConstructorParameterIndicator() {
    return false;
  }

  @Override
  public boolean isMethodParameterIndicator() {
    return false;
  }
}