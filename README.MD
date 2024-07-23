# Invoice Demo

This application is a demonstration of a spring boot server which provides an API to read invoices and an Angular
frontend, which visualizes them using the API.

## API

The API is defined as an OpenApi spec under [openapi/InvoiceApi.yaml](openapi/InvoiceApi.yaml). From this spec model
classes are generated in the frontend and backend. Furthermore, Spring Boot Controller to expose the API and Angular
Services to consume the API are generated.

## Database

The data is stored in an in memory H2 database. It is initially filled with demo data. If you want to change the demo
data which is passed, you can modify [data.sql](invoicedemo-server/src/main/resources/data.sql), which is responsible
for writing the data. Data access in the application is implemented via Jooq, and the database model classes are 
generated with jooq-codegen from the [schema.sql](invoicedemo-server/src/main/resources/schema.sql). 

## Frontend

The frontend is an Angular 18 application. It is packaged as a webjar and delivered as a static resource by the 
Spring-Boot server. 

# Running the application

## Prerequisites

This project uses Gradle as its build tool. In order to run gradle to build this project, you need to have at least a 
Java 21 JDK installed. 

## Build and run the application

To build the application, simply rund `./gradlew clean build` in the project root directory. 

To run the application run `./gradlew clean bootRun` instead. 
The server will start at `localhost:8080`. The angular frontend is accessible under the root path `http://localhost:8080`
and the APIs under their respective paths (see [OpenApi spec](openapi/InvoiceApi.yaml)). 

## Demo

If any troubles occur during build, you can fall back to a pebuilt JAR in the [demo](demo) directory. 
If you have docker installed you can simply run `docker-compose up` and a docker image will be built containing the JAR
and also be run. 


