FROM openjdk:21
WORKDIR /server

ADD ./build/libs/backend-all.jar /server/
CMD ["java", "-Xmx128M", "-jar", "backend-all.jar"]
