FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/savethechildren-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} savethechildren-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/savethechildren-0.0.1-SNAPSHOT.jar"]