package com.visa.gpd.data.jpa.service;

import com.visa.gpd.data.jpa.entity.Address;
import com.visa.gpd.data.jpa.entity.Consumer;
import com.visa.gpd.data.jpa.repository.ConsumerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: younglee
 * Date: 8/6/14
 */
@Service
public class ConsumerService {
  @Resource
  private ConsumerRepository consumerRepository;

  @Transactional
  public Consumer consumer() {
    Consumer consumer = new Consumer();
    consumer.setFirstname("Young");
    consumer.setLastname("Lee");
    consumer.setEmailAddress("youngl@gmail.com");

    Address address = new Address();
    address.setStreet("901 Metro Center Blvd.");
    address.setCity("Foster City");
    address.setState("CA");
    address.setZip("94401");
    address.setConsumer(consumer);

    List<Address> addresses= new ArrayList<Address>();
    addresses.add(address);
    consumer.setAddresses(addresses);

    consumerRepository.save(consumer);
    Consumer consumer1 = consumerRepository.findOne(consumer.getId());
    return consumer1;
  }

  @Transactional
  public Consumer getconsumer(@PathParam("id") Long id) {
    Consumer consumer = consumerRepository.findOne(id);
    return consumer;
  }

}
