This is the same app as the other one in my repositories, called offers but this one uses the VAVR framework(formerly javaslang)

To boot the app:
```
./gradlew bootRun
```

Sample Requests:
```
curl --location --request POST 'http://localhost:8080/offers/create' \
--header 'Content-Type: application/json' \
--data-raw '{
	"description": "Offer Details",
	"price": "2.50",
	"currency": "GBP",
	"expiration": "2020-02-15"
}'


curl --location --request GET 'http://localhost:8080/offers/d8a73bfc-7aaa-4097-896c-ecb0ee25c441'



curl --location --request GET 'http://localhost:8080/offers/d8a73bfc-7aaa-4097-896c-ecb0ee25c441/cancel'
```
