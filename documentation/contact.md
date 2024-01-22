# Contact API Spec

## Create Contact
Endpoint : POST /api/contacts

Request Header :
- X-API-TOKEN : Mandatory (Token)

Request Body : 
```json
{
  "firstName": "Ryan",
  "lastName": "Dinul Fatah",
  "email": "dinulfatahrayn@gmail.com",
  "phone": "0812376343"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "firstName": "Ryan",
    "lastName": "Dinul Fatah",
    "email": "dinulfatahrayn@gmail.com",
    "phone": "0812376343"
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


## Update Contact 
Endpoint : PUT /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Mandatory (Token)

Request Body :
```json
{
  "firstName": "Ryan",
  "lastName": "Dinul Fatah",
  "email": "dinulfatahrayn@gmail.com",
  "phone": "0812376343"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "firstName": "Ryan",
    "lastName": "Dinul Fatah",
    "email": "dinulfatahrayn@gmail.com",
    "phone": "0812376343"
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

## Get Contact 
Endpoint : GET /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body (Success) :
```json
{
  "data": {
    "id": "string",
    "firstName": "Ryan",
    "lastName": "Dinul Fatah",
    "email": "dinulfatahrayn@gmail.com",
    "phone": "0812376343"
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

## Search Contact 
Endpoint : GET /api/contacts/

Request Params (All Optional) :
- name : String, first name and last name, using like query
- phone : String, contact phone, using like query
- email : String, contact optional, using like query
- page : Integer, start from 0
- size : Integer, default 10

```json
{
  "data": [
    {
        "id": "string",
        "firstName": "Ryan",
        "lastName": "Dinul Fatah",
        "email": "dinulfatahrayn@gmail.com",
        "phone": "0812376343"
    }
  ],
  "paging": {
    "totalPages": 10,
    "size": 10,
    "currentPage": 0
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

## Remove Contact 
Endpoint : DELETE /api/contacts/{idContact}

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