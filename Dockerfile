FROM openjdk:12
EXPOSE 8080
ADD /build/libs/yellow-0.0.1-SNAPSHOT.jar yellow-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "yellow-0.0.1-SNAPSHOT.jar"]