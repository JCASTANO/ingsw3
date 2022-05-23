FROM openjdk:17-alpine

MAINTAINER uco.com

COPY ./build/libs/myproject-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "myproject-0.0.1-SNAPSHOT.jar"]