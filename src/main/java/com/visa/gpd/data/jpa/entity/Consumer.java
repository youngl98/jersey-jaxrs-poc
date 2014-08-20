package com.visa.gpd.data.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: younglee
 * Date: 8/5/14
 */
@Entity
@Table(name = "consumer")
public class Consumer extends AbstractEntity {

  @Column(name = "first", length = 60)
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @Column(name = "last", length = 50)
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Column(name = "email", length = 100)
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "consumer")
  public List<Address> getAddresses() {
    return Collections.unmodifiableList(addresses);
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  private String firstname;
  private String lastname;
  private String emailAddress;
  private List<Address> addresses = new ArrayList<Address>(0);

  @Override
  public String toString() {
    return "Consumer{" +
           "firstname='" + firstname + '\'' +
           ", lastname='" + lastname + '\'' +
           ", emailAddress='" + emailAddress + '\'' +
           ", addresses=" + addresses +
           '}';
  }
}
