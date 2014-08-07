package com.visa.gpd.jersey.resource;

import com.visa.gpd.data.jpa.entity.Consumer;
import com.visa.gpd.data.jpa.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class ConsumerResource {

      /**
       * Method handling HTTP GET requests. The returned object will be sent
       * to the client as "text/plain" media type.
       *
       * @return String that will be returned as a text/plain response.
       */

  @POST
  @Path("consumer")
  //@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces(MediaType.APPLICATION_JSON)
  public Consumer consumer() {
    return consumerService.consumer();
  }

  @GET
  @Path("consumer/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Consumer getconsumer(@PathParam("id") Long id) {
    return consumerService.getconsumer(id);
  }

  @Autowired
  private ConsumerService consumerService;
}
