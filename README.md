# CORE PLATFORM

[Problem Statement](./Prueba%20Precios%20TEXT.docx)

This project use Flyway to handle modifications to the database H2, the project has been Dockerized, and  
it runs with a "docker-compose up" command, coverage is above 100%, it uses ParameterizedTest to perform all  
the tests suggested by the exercise document.

Three endpoints were built:
* To get all the prices from the database H2 ("/api/v1/all")
* To get the applicable ranges for a specific target date ("/api/v1/ranges")

## docker-compose up

![Forcing Dockerfile](./images/Screenshot%20from%202025-04-21%2001-06-47.png)  
![Spin up completed](./images/Screenshot%20from%202025-04-21%2001-09-15.png)

## H2

![H2 Login Console](./images/Screenshot%20from%202025-04-21%2001-00-44.png)  
![H2 Database](./images/Screenshot%20from%202025-04-21%2001-00-58.png)

## getting all the prices from the database H2 ("/api/v1/all")

![all](./images/Screenshot%20from%202025-04-21%2001-02-06.png)

## getting the applicable ranges for a specific target date ("/api/v1/ranges")

When there is more than one result, the item with the highest priority show up at the top.

![ranges_one](./images/Screenshot%20from%202025-04-21%2001-21-46.png)  
![ranges_two](./images/Screenshot%20from%202025-04-21%2001-22-06.png)




