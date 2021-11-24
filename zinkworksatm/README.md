#Running the application
To start the application:
mvn spring-boot:run

#Status
I was refactoring code when I discovered that the database I was using
did not seem to be populated - this results in the endpoints below
giving HTTP codes in the 400 range only. I have tried to fix this, but I
seem to have spent more time than I intended solving that problem to no avail.

#Endpoints
* GET localhost:8080/account/{accountNumber}/{pin}
* POST localhost:8080/cash/{accountNumber}/{pin}/{amount}
