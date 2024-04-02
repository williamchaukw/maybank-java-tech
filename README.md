# Getting Started - Maybank Technical Test

## Database Preparation Script
* Please find TESTDB-TABLE-Script SQL script in /src/main/java/resources folder
* Run it into local MSSQL server

## Pre-requisite for request
* Set header key value "Authorization: token"

## Available endpoints
### /getClientByUsername (Params: username)
* Request params: (Mandatory) username
* Response: Name and area of customer
### /getAllClients
* Request params: (Optional) page number, (Optional) page size
* Response: List of clients
### /createClient
* Request params: (Mandatory) username, (Mandatory) name, (Mandatory) area
* Response: status of customer creation
### /updateClient/{username}
* Request params: (Optional) name, (Optional) area
* Response: status of customer update
### /getQuotes
* Request params: N/A
* Response: Random quote string
* /getQuotes

