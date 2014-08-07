package com.visa.gpd.jersey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * User: younglee
 * Date: 8/1/14
 */
@Provider
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {

  final ObjectMapper combinedObjectMapper;

  public MyObjectMapperProvider() {
    combinedObjectMapper = createCombinedObjectMapper();
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {

    return combinedObjectMapper;
  }

  private static ObjectMapper createCombinedObjectMapper() {
    return new ObjectMapper()
        //.configure(SerializationFeature.WRAP_ROOT_VALUE, true)
        //.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
  }

  private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

    // Look for either the JAXB or JSON annotations on pojos.  Makes it really convenient for
    // development as one is not limited with XML centric view of the POJOS.

    final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
    final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

    return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
  }
}