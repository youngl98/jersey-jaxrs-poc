jersey-jaxrs-poc
================

JAX-RS 2.0 POC with Spring Data , Hibernate, servlet 3.0 containers with no XML configuration

How to run
----------
To start the tomcat container from the project

> mvn tomcat7:run

Use the browser or curl to create a Consumer and to query based on the consumer id

> curl -XPOST http://localhost:8080/jersey-jaxrs-poc/myresource/consumer

> curl http://localhost:8080/jersey-jaxrs-poc/myresource/consumer/1
