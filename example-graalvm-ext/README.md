
mvn spring-boot:build-image

Test:

docker run --rm -p 8080:8080 example-graalvm-ext:0.0.1-SNAPSHOT

Docu:

https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/