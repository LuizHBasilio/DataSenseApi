# DataSense API

This project is an application for monitoring sensors in an industrial plant. The solution includes an API for receiving and storing sensor data and a user interface for viewing average sensor values ​​over different time periods.

You can access the API at http://localhost:8081/datasense/api/v1/equipments

## API Endpoints
The DataSense API defines the following endpoints:

### GET
Returns all sensor data.

Response
Status Code: 200 OK

Content-Type: application/json

Response Body:

```json
[
    {
        "id": 1,
        "equipmentId": "EQ-12495",
        "timestamp": "2023-02-15T03:30:00-03:00",
        "value": 78.42
    },
    {
        "id": 2,
        "equipmentId": "EQ-12495",
        "timestamp": "2023-02-15T03:30:00-03:00",
        "value": 78.42
    },
    {
        "id": 3,
        "equipmentId": "EQ-12495",
        "timestamp": "2024-07-10T03:30:00-03:00",
        "value": 78.42
    }
]
```

### GET /{id}
Returns data from a specific sensor by ID.

Request Parameters
id - The ID of the sensor to retrieve.

Response
Status Code: 200 OK

Content-Type: application/json

Response Body:

```json
{
    "id": 4,
    "equipmentId": "EQ-12495",
    "timestamp": "2024-07-10T03:30:00-03:00",
    "value": 120.22
}
```

Status Code: 404 Not Found

Content-Type: application/json

Response Body:

```json
{
  "error": "Equipment with id 13 was not found"
}
```

### POST
Receives data from sensors in JSON format and stores it in the database.

Request Body
Content-Type: application/json

Request Body:

```json
{ 
  "equipmentId": "EQ-12495", 
  "timestamp": "2024-07-11T01:30:00.000-05:00", 
  "value": 78.42 
}
```

Response
Status Code: 200 OK

Content-Type: application/json

Response Body:

```json
{
    "id": 8,
    "equipmentId": "EQ-12495",
    "timestamp": "2024-07-11T06:30:00Z",
    "value": 78.42
}
```

Status Code: 400 Bad Request

Content-Type: application/json

{
    "timestamp": "2024-07-11T16:36:32.364+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/datasense/api/v1/equipments/"
}

### POST /upload
Receives a CSV file, parses the data and stores it in the database.

Request Body
Content-Type: application/json

Request Body:

```form-data/file
equipmentId,timestamp,value
EQ-12425,2023-02-15T01:30:00.000-05:00,78.42
```

Response
Status Code: 200 OK

Content-Type: application/json

Response Body:

```json
{
  "CSV file was been processed"
}
```

Status Code: 500 Server Error

Content-Type: application/json

Response Body:

```json
{
  "error": "Error in CSV file"
}
```
