FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
CMD []
ENTRYPOINT ["java","-jar","/app.jar"]