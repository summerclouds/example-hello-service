
mvn spring-boot:build-image 

Test:

docker run --rm -p 8080:8080 example-container:0.0.1-SNAPSHOT
http://localhost:8080/hello
http://localhost:8080/actuator/health

The health status will rotate between UP, OUT_OF_SERVICE, DOWN every minute.

Docu:

https://reflectoring.io/spring-boot-health-check/
