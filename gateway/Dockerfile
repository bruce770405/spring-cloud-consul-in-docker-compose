FROM maven:alpine
MAINTAINER BruceHsu <bruce770405@gmail.com>

EXPOSE 8763
ADD ./ /app/spring-cloud-gateway/
WORKDIR /app/spring-cloud-gateway/
RUN mvn package
VOLUME /gateway
WORKDIR /
ENTRYPOINT ["java", "-jar",\
            "/app/spring-cloud-gateway/target/server-gateway-1.0.0-SNAPSHOT.jar"]