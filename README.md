# My HR Companion Instructions

Welcome to My HR Companion API. Please follow these instructions to get the application running on your local machine.

### Create .env file

To get started:

1. Create a new .env file in the root of the project
2. Add the following as entries in your .env file:
    
    - POSTGRES_DB={your secret value}
    - POSTGRES_HOST={your secret value}
    - POSTGRES_USER={your secret value}
    - POSTGRES_PASSWORD={your secret value}
    - JWT_SECRET={your secret value}
    - JWT_ACCESS_TOKEN_EXPIRATION={your secret value}
    - JWT_REFRESH_TOKEN_EXPIRATION={your secret value}

The access token and refresh token should be in milliseconds.

### Postgres Docker (You need to have docker installed)

There is a docker-compoese.yml file in the root of the project. This is to spin up a postgres database for development
purposes. The secret values will be pulled from the .env file and used to connect to the DB with creds in the
application.properties file.

### Application properties

This will have the configs for you DB and JWT settings (and any other settings in the future.)
Please ensure all secrets are stored in the .env file then referenced in the application.properties for obvious security
reasons :)

### Running the project

To get the project running:

1. Run `docker-compose up -d` from the root of the project from a terminal window to start the local database
2. Run the spring boot project as normal
3. That's it!! You can start testing endpoints with postman or from a dedicated front end application

# Swagger API Documentation

To access and view the Swagger API documentation for My HR Companion, please use the endpoint `/swagger-ui/index.html` after starting the application. This will provide you with a comprehensive overview of all available API endpoints, their request and response formats, and other relevant details to help you interact with the API effectively.