# Library Manager REST API - Backend

[![Build Status](https://travis-ci.org/kamisc/LibraryManager-Backend.svg?branch=master)](https://travis-ci.org/kamisc/LibraryManager-Backend)

## Description
In this repository you can find REST API code written in Java language for Library Manager project. 
To start working with the application, you should first run the backend app and then frontend app. 

[Check Live Demo](https://library-manager-frontend.herokuapp.com/login)
   
        Admin credentials: L: admin@library.com / P: admin
        User credentials: create an account with your own credentials :-) 

## Frameworks and Technologies
Java 8, Spring Boot, Spring Security (Json Web Token), Hibernate, JUnit4, Mockito, Lombok, Gradle, Swagger, H2 In Memory Database

## Design Patterns
Factory, Observer

## External Services
1. Audiobooks from **_https://wolnelektury.pl/api/_**
2. Top Stories from **_https://developer.nytimes.com/apis_**

## Instalation
#### Backend - REST API
1. Source code: https://github.com/kamisc/LibraryManager-Backend
2. git clone https://github.com/kamisc/LibraryManager-Backend.git
3. Run application by the main method in the LibraryManagerBackendApplication class
4. Application will be run on the localhost:8080 address

#### Frontend
1. Source code: https://github.com/kamisc/LibraryManager-Frontend
2. git clone https://github.com/kamisc/LibraryManager-Frontend.git
3. Run application by the main method in the LibraryManagerFrontendApplication class
4. Application will be run on the localhost:8090 address

#### Users
In the login panel click "Create new account" button and:

        Use --> admin@library.com <-- e-mail to create ADMIN user to test all of functions
        
        Use any mail to create NORMAL user to test functions for NORMAL user
        
#### Other
**E-mail configuration:** configure environment variables **_MAIL_USERNAME_** and **_MAIL_PASSWORD_** for the gmail account. 
Or set gmail username and password directly in the application.properties file. Remember that your gmail account must have
permission to access account for less secure applications.

**_Author: Kamil Seweryn_**