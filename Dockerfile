FROM frolvlad/alpine-oraclejre8:latest

MAINTAINER Alexander von Trostorff <alexander.von.trostorff@gmail.com>

VOLUME /tmp
COPY target/java-backend-api-*.jar app.jar
ENTRYPOINT ["java", "-server", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
