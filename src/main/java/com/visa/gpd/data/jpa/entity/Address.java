package com.visa.gpd.data.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User: younglee
 * Date: 8/5/14
 */
@Entity
@Table(name = "address")
public class Address extends AbstractEntity {

  @Column(name = "street", length = 255)
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Column(name = "city", length = 100)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Column(name = "state", length = 20)
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Column(name = "zip", length = 10)
  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  public Consumer getConsumer() {
    return consumer;
  }

  public void setConsumer(Consumer consumer) {
    this.consumer = consumer;
  }

  private String street;
  private String city;
  private String state;
  private String zip;
  private Consumer consumer;

  @Override
  public String toString() {
    return "Address{" +
           "street='" + street + '\'' +
           ", city='" + city + '\'' +
           ", state='" + state + '\'' +
           ", zip='" + zip + '\'' +
           '}';
  }
}
