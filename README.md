# E-Commerce back-end application built in Java and Spring Boot

A Spring application for selling goods online with functionality to run discounts
and deals. Application uses REST endpoints to create, read, update and delete products to sell, discounts for each product, deals for multiple products and also endpoints to add and remove products to customer's cart. The discounts and deals are calculated while these products are being added and removed from the cart.

Project demonstrates how to utilize
- Spring Boot to create a web application and implement RESTful APIs and MVC paradigm
- Spring Data JPA to implement data access layer to create database entities, map different relations between tables and implement optimistic locking mechanism for handling concurrent requests
- H2 database to spin up a quick and easy in memory database instance

## Technologies used
- Spring Boot for web application with embedded tomcat server
- Spring Data JPA for data access layer
- H2 database that stands up a new clean instance everytime the app starts with the DB schema shown below
- JUnit for unit tests
- SwaggerUI for documentation


## Requirements</br>
Java 11<br/>
Gradle 5

## Commands</br>
starting the application 
```
./gradlew bootRun
```
running unit tests
```
./gradlew test
```

## Database schema
![database schema](docs/ecommerceSchema.jpg)

## REST end points documentation
[SwaggerUI](docs/ecommerceSwaggerUI.pdf)