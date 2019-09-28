FROM "openjdk:11-jdk"
MAINTAINER Marcos Mele
COPY target/desafio-0.0.1-SNAPSHOT.jar starwars.jar
ARG ambiente
ENV envambiente=${ambiente}
ENTRYPOINT ["java", "-Dspring.profiles.active=${envambiente}", "-jar", "/starwars.jar"]