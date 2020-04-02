FROM maven:slim
MAINTAINER BruceHsu <bruce770405@gmail.com>

EXPOSE 8762
ADD ./ /opt/spring-cloud-config-server/
WORKDIR /opt/spring-cloud-config-server/
RUN mvn package
VOLUME /config
WORKDIR /
# "-Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["java", "-jar","/opt/spring-cloud-config-server/target/config-server-0.0.1-SNAPSHOT.jar","--server.port=8762"]