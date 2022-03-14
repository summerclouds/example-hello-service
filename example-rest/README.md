# example-hello-service

Start Jaeger UI

```
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.32
```

Start OTEL Collector:

```
docker run -d --name otel-collector \
  -v otel-collector-config.yml:/etc/otel-collector-config.yml \
  -p 1888:1888 \
  -p 8888:8888 \
  -p 8889:8889 \
  -p 13133:13133 \
  -p 55679:55679 \
  --link jaeger:jaeger \
  otel/opentelemetry-collector
```

Test

```
http://localhost:8080/hello

http://localhost:8080/error400

Jaeger UI:
http://localhost:16686/

```

Access

user - user


