package com.visa.gpd.data.jpa;

import com.visa.gpd.data.jpa.repository.ConsumerRepository;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * User: younglee
 * Date: 8/5/14
 */
public class ApplicationConfigTest {

  @Test
  public void bootstrapAppFromJavaConfig() {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    assertThat(context, is(notNullValue()));
    assertThat(context.getBean(ConsumerRepository.class), is(notNullValue()));
  }
}
