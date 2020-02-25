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
