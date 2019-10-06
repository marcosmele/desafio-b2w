FROM "openjdk:11-jdk"
MAINTAINER Marcos Mele
COPY target/desafio-0.0.1-SNAPSHOT.jar starwars.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-jar", "/starwars.jar"]