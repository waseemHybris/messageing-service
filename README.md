# messageing-service


# Read Me First

The api definition can be found in api-description.yaml file provided in the resources folder.

To bring up or down the docker containers defined in `docker-compose.yml`, run

    docker-compose up
    docker-compose down

To force delete the volume, and start from scratch (removes any data stored in the database)

    docker-compose up --force-recreate --build
    docker-compose down --volumes --remove-orphans

# Getting Started
To run the service locally, you need to have:
* Git
* Docker
* Java 13
* Maven 3

1- bring up docker container by navigating to the project root in your favorite terminal and running:
 `docker compose up`

2- start spring boot application by running:

`mvn clean install`
then
`mvn spring-boot:run`

At this point, you should be able to start making post request to the service url:
`http://localhost:8090/messages`

you can refer to the postman collection for a sample request.




