//
// Built on Tue Aug 19 17:59:54 CEST 2014 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.DEBUG

appender("CONSOLE", ConsoleAppender) {
  layout(PatternLayout) {
    pattern = "%d [%thread] %level %logger - %m%n"
  }
}
root(INFO, ["CONSOLE"])

logger 'org.hibernate.SQL', DEBUG