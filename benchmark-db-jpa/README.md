

docker build --build-arg JAR_FILE=target/benchmark-db-jpa-0.0.1-SNAPSHOT.jar -t benchmark-db-jpa:0.0.1-SNAPSHOT .

docker run --rm benchmark-db-jpa:0.0.1-SNAPSHOT

mariadb: see benchmark-db-adb

docker run --rm \
 --link mariadb:mariadb \
 --env spring.datasource.driver=com.mysql.jdbc.Driver \
 --env spring.datasource.url=jdbc:mysql://mariadb/test \
 --env spring.datasource.username=myuser \
 --env spring.datasource.password=my_cool_secret \
 --env spring.jpa.hibernate.ddl-auto=update \
 benchmark-db-jpa:0.0.1-SNAPSHOT -c 100000 -l 100
