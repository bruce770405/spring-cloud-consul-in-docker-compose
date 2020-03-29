FROM maven:alpine
MAINTAINER BruceHsu <bruce770405@gmail.com>

EXPOSE 8762
ADD ./ /app/spring-cloud-config-server/
WORKDIR /app/spring-cloud-config-server/
RUN mvn package
VOLUME /config
WORKDIR /
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar",\
            "/app/spring-cloud-config-server/target/server-config-1.0.0-SNAPSHOT.war",\
            "--server.port=8762",\
            "--spring.config.name=configserver"]