package com.visa.gpd.data.jpa;

import com.visa.gpd.data.jpa.entity.Consumer;
import com.visa.gpd.data.jpa.repository.ConsumerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * User: younglee
 * Date: 8/5/14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class ConsumerRepoTest {

  @Autowired
  private ConsumerRepository consumerRepository;

  @Test
  public void createConsumer() {
    Consumer consumer = new Consumer();
    consumer.setFirstname("Young");
    consumer.setLastname("Lee");
    consumer.setEmailAddress("youngl@gmail.com");

    assertNull(consumer.getId());

    consumerRepository.save(consumer);

    assertNotNull(consumer.getId());
  }
}
