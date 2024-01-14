# User API Spec

## Register API

- Endpoint : POST /api/users

Request Body :
```json
{
  "name": "Ryan Dinul Fatah",
  "username": "ryan",
  "password": "password"
}
```

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

## Login User API

- Endpoint : POST /api/auth/login

Request Body :
```json
{
  "username": "ryan",
  "password": "password"
}
```

Response Body (Success) :
```json
{
  "data": {
    "token": "TOKEN",
    "expiredAt": 12345
  }
}
```
// milliseconds for expiredAt

Response Body (Failed) :
```json
{
  "data": "KO",
  "errors": "Error message here!"
}
```

## Get User API

- Endpoint : GET /api/users/current

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body (Success) :
```json
{
  "data": {
    "name": "Ryan Dinul Fatah",
    "username": "ryan"
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

## Update User API

- Endpoint : PATCH /api/users/current

Request Header :
- X-API-TOKEN : Mandatory (Token)

Request Body (Success) :
```json
{
  "data": {
    "name": "Ryan Dinul Fatah",
    "password": "new password"
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

## Logout User API

- Endpoint : DELETE /api/auth/logout

Request Header :
- X-API-TOKEN : Mandatory (Token)

Response Body :
```json
{
  "data": "OK"
}
```