# Address API Spec

## Create Address
Endpoint : POST /api/contact/{idContact}/addresses

Request Header :
- X-API-TOKEN : Mandatory (Token)

Request Body :
```json
{
  "street": "Jalan Apa",
  "city": "City",
  "province": "Zakar",
  "country": "ID",
  "postalCode": "12432"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "street": "Jalan Apa",
    "city": "City",
    "province": "Zakar",
    "country": "ID",
    "postalCode": "12432"
  }
}
```

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```


## Update Address
Endpoint : PATCH /api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : Mandatory (Token)

Request Body :
```json
{
  "id": "string",
  "street": "Jalan Apa",
  "city": "City",
  "province": "Zakar",
  "country": "ID",
  "postalCode": "12432"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "street": "Jalan Apa",
    "city": "City",
    "province": "Zakar",
    "country": "ID",
    "postalCode": "12432"
  }
}
```

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```

## Get Address
Endpoint : GET /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "street": "Jalan Apa",
    "city": "City",
    "province": "Zakar",
    "country": "ID",
    "postalCode": "12432"
  }
}
```

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```

## Remove Address
Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body (Success) :
```json
{
  "data": "OK"
}
```

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```

## List Address
Endpoint : GET /api/contact/{idContact}/addresses

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body (Success) :
```json
{
  "data": [
    {
      "id": "string",
      "street": "Jalan Apa",
      "city": "City",
      "province": "Zakar",
      "country": "ID",
      "postalCode": "12432"
    }
  ]
}
```

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```