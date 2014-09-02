wikiboot
========

## Work In Progress

This is very much a **Work In Progress**, so please have a look, but I really do not want issue reports saying **this does not work**, in many ways that is true.

Do not expect a fully functional application (might come eventually), for now this is an experimental sandbox to get to know some new technologies.

If you notice something that could be improved (lots of that), I welcome **Pull Requests**.

## Tech Used (work in progress)

Some of these I'm not really happy with and might get replaced (any sane JavaScript package management tools out there?).

### Server side

The short list (there are more):

* Spring Boot (latest snapshot) with Spring 4.1
* Spring Data JPA/REST
* Jetty 9 (embedded)
* Java 8
 
### Client side

* AngularJS (with Restangular, Bootstrap UI and some others)
* Bootstrap (themed by Bootswatch)
* Curl.js

### Build and dependency management

* Gradle
* Gulp
* Bower

## Building and running

Have a look at `/run.sh`. There is an issue with the client packaging at the moment, so `/run-jar.sh` does not work.
