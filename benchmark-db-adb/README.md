



mvn package

docker build --build-arg JAR_FILE=target/benchmark-db-adb-0.0.1-SNAPSHOT.jar -t benchmark-db-adb:0.0.1-SNAPSHOT .

docker run --rm benchmark-db-adb:0.0.1-SNAPSHOT

---

docker run --name mariadb -d \
 --env MARIADB_ROOT_PASSWORD=my-secret-pw \
 -v $(pwd)/sql:/docker-entrypoint-initdb.d:ro \
 mariadb:10.7.3

docker run --rm \
 --link mariadb:mariadb \
 --env xdb.default.pool.rw.driver=com.mysql.jdbc.Driver \
 --env xdb.default.pool.rw.url=jdbc:mysql://mariadb/test \
 --env xdb.default.pool.rw.user=myuser \
 --env xdb.default.pool.rw.password=my_cool_secret \
 benchmark-db-adb:0.0.1-SNAPSHOT

 
docker stop mariadb
docker rm mariadb
 
docker logs mariadb
docker exec -it mariadb bash 
docker exec -it mariadb ls /docker-entrypoint-initdb.d


 