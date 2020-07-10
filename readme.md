## Service Desk application

This is service desk application for Eesti Energia test assignement:

## Assigment
Test exercise - Service Desk

Create simple Service Desk application. In application you can open new tickets. Creating
new tickets must happen in separate modal window.
Ticket should contain folowing fields:
*  Title
* Automatically generated ticket number
* E-mail aadress
*' Problem description
* Five level priority system

After confirming ticket, it is shown in list of tickets. Tickets should be abel to change after
creation. After closing ticket, it should be removed from list. Tickets list should be sortable by
status, priotiry and date.
Bear in mind that this exercise must use best practices in development world. This application
must be covered with tests.

## Application
Application is ran with . For data storage H2 in memory database is
used. Data transfer between database and application is managed by CrudRepository and JPA.

## Running application
You can use maven wrapper functionality to run this project:
1) ./mvnw clean install
This will download all dependencies and install application to local m2 storage 
2) ./mvnw spring-boot:run
This will run the application, after that you can access it in http://localhost:8080/

## Design decisions
Here are explanations, why given solution was chosen and what alternatives considered.

Front End: 
Html pages with Thymeleaf template engine, which has good integration with Spring MVC.
This allows iterating over provided values and binding of form values and errors.
Other consideration was single page application where all data is being moved Ajax requests. 
Decided to go for Thymeleaf, because otherwise I had to do form value and errors binding manually in Javascript.

Database:
H2 in memory data storage was chosen because test assignment didn't provide any requirement
of storing data over multiple starts. Also Spring Boot provides out of the box CrudRepository with
basic CRUD functionalities, which were sufficient for storing and retrieving data. This ables
to skip defining external dependency, which was required to run application.

Lombok:
Remove need for boilerplate code.
