FROM maven:slim
MAINTAINER BruceHsu <bruce770405@gmail.com>

EXPOSE 8763
ADD ./ /app/spring-cloud-gateway/
WORKDIR /app/spring-cloud-gateway/
RUN mvn package
VOLUME /gateway
WORKDIR /
ENTRYPOINT ["java", "-jar",\
            "/app/spring-cloud-gateway/target/gateway-0.0.1-SNAPSHOT.jar"]