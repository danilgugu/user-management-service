# User Management Service

## A RESTful User Management Service with Token-Based Authorization, Dockerized PostgreSQL Database, Setup Scripts, Spring Boot, Spring Cache and Login Endpoint

## Run

To run this app locally you need a Docker to be installed. If you don't have it yet, you can download it
[for MasOs](https://docs.docker.com/desktop/install/mac-install/),
[for Windows](https://docs.docker.com/desktop/install/windows-install/) or
[for Linux](https://docs.docker.com/desktop/install/linux-install/).

Once you're done with installation, run Docker Desktop, then open terminal in root of this project and paste
`docker compose up -d`. It will take several minutes for the first time.

## Use

To use this app locally, you can perform HTTP requests using postman or IDE if you have it. Please check out
the [request-examples](/request-examples) folder. All possible request examples are stored there, and you can execute
them straightaway from your IDE if it allows you to do so.

Other way to try local requests is to visit [swagger-ui](http://localhost:8080/swagger-ui/index.html#/).

### Create user

Choose [createUser](/request-examples/createUser.rest) request if you want to register a new user. Everyone is allowed
to do it. You cannot use email that is already used in a system by other user.

### Delete user

[deleteUser](/request-examples/deleteUser.rest) request could be performed only by admins. There is one admin pre-added
in a system, so you can use its token to delete user. If user doesn't exist, you will get an error trying to delete it.

### Get user

[getUser](request-examples/getUser.rest) returns information about selected user. You need to know its id to make a
request. You need to be registered to make this request, but no additional permissions needed. You will get an error if
user doesn't exist.

### Get users

[getUsers](request-examples/getUsers.rest) returns list of all users in the system. You don't need to be registered for
it. This is a cacheable request. Any time you request it again it will not go to the database using cache instead. Any
successful user creation or deletion leads to cache evicting. Cache will be also cleaned up automatically in one hour
after last save.

### Login

If you need a token for your user to access authorized requests, go to [login](request-examples/login.rest) request.
Type your credentials and run request. Now you can use token from the response as an authorization header (see in
examples above). If you mistype your credentials you will get an error.

### Update user

[updateUser](request-examples/updateUser.rest) needed if you want to update your or someone else user details. You need
to be registered to make this request. You need to know user id of user you are trying to update. If user will not be
found by id you will get an error. If you try to update email with some value that is already in use you will get an
error.

## Stop

If you want to stop the application and save everything as it is in database, simply type `docker compose stop` in
terminal in root of this project. After it, you can start the application again with `docker compose up -d`, this time
it should be much faster.

## Stop and remove

If you want to stop the application and remove everything from the database, type `docker compose down` in terminal in
root of this project. After it, you can start the application again with `docker compose up -d`, but all the data in
database will be lost.
