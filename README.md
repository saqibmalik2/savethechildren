# savethechildren

Instructions for creating the docker image

1. Create the exectuable jar file via the **mvn clean install** command.
2. Navigate to the project root directory where you will find the **Dockerfile**
3. Run the following command: **docker build -t savethechildren .**
4. Verify the image has been created by running: **docker images**
5. Run the docker container via: **docker run -p 8080:8080 savethechildren**

You can use Postman or simple curl commands to test the POST and GET endpoints
or you can access all the endppoints via the lovely Swagger UI at the following URL: **http://localhost:8080/swagger-ui/index.html**