FROM openjdk:8-jdk-alpine

RUN addgroup -S paymentchain && adduser -S admin -G paymentchain

USER admin:paymentchain

VOLUME /tmp

ARG JAR_FILE=target/*.jar

ADD target/${JAR_FILE} app.jar

ENV JAVA_OPTS=$JAVA_OPTS

# COPY target/product-1.0.0-SNAPSHOT.jar paymentchainparent.jar

# EXPOSE 8083

# ENTRYPOINT exec java $JAVA_OPTS -jar paymentchainparent.jar

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar paymentchainparent.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
