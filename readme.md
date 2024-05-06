# Example application

## Table of Contents

- [backend](#backend)
- [frontend](#frontend)
- [nginx](#nginx)


## backend
- provides an example springboot (v3) application
You can build and run this project as:
- import an IDE as maven project (IntelIj, SprintToolSuite) and run as springboot application
- in console ./mvnw spring-boot:run 
- if you want to run in docker (in `backend` folder): `docker run --rm -it -v $(pwd):/app -v $(pwd)/.m2:/.m2 -w /app -p 8080:8080 -u $(id -u):$(id -g) openjdk:21-jdk 'bash -c "./mvnw spring-boot:run"'`

OK URL: http://localhost:8080/api/OK
H2 database console URL: http://localhost:8080/h2-console
(all settings can be found in the application.properties file)


## frontend

You can start in docker in `frontend` folder:

`docker run --rm -it -v $(pwd):/app -u $(id -u):$(id -g) -p 3000:3000 -w /app node:latest ./start.sh`

Or you can start without docker: [frontend](frontend/readme.md)

## nginx
