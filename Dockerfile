FROM eclipse-temurin:17-jre-focal

ADD build/libs/coreplatform-0.0.1-SNAPSHOT.jar app.jar
ADD src/main/resources/application.properties application.properties
RUN sh -c 'touch ./app.jar'
RUN apt-get install curl
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar ./app.jar"]
