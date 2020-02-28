This is a demo. A microservice that uses spring-kafka
At the moment there's an endpoint that will trigger the generation of an event
but this endpoint will be removed once tested in integration. I just created the endpoint
temporarily for manual testing purposes.

To boot the app:
```
./gradlew bootRun
```

Sample Requests:
```
curl --location --request POST 'http://localhost:8080/offers/kafka/create' \
--header 'Content-Type: application/json' \
--data-raw '{
	"description": "Offer Details",
	"price": "2.50",
	"currency": "GBP",
	"expiration": "2020-02-15"
}'

```

This app requires that Kafka and Zookeeper are running in a docker instance and also have a
topic called "creation" to setup this prerequisites you need to follow this steps:

Run the compose command and make sure Kafka is running
```
docker-compose up -d
```

Find the container id
```
docker-compose up -d
```

Log into docker container for Kafka
```
docker exec -it  <containerId> /bin/bash  
```

Create topic
```
cd /usr/bin
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```

Check if topic exists
```
cd /usr/bin
kafka-topics --list --bootstrap-server localhost:9092
```