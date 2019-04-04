FROM frolvlad/alpine-oraclejdk8:slim

MAINTAINER Alexander von Trostorff <alexander.von.trostorff@gmail.com>

VOLUME /tmp
COPY target/java-backend-api-*.jar app.jar
ENTRYPOINT ["java", "-server", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
