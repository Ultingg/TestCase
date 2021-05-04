# TestCase
This is server part of RESTful web-application. Working with HTTP responses and JSON.
It's working with Postgres DB, there are  scripts to initialize DB in resources folder: schema.sql (to create databse and table) and data.sql (to settle table).
Also, there is postman collection of requests in postmanCollection folder to test application.

You could get User by GET request, you could add new User by POST request, and you could update User's status by PUT request. User's "Online" status has expired time - 5 minutes, 
if you don't update it in time it becomes "Away".



Here is requests:

GET: http://localhost:8080/users/1  - to getting user's info with id = 1;

POST: http://localhost:8080/users/  - to adding new User to DB, you should pass User in JSON format;

PUT: http://localhost:8080/users/1  - to update User's status, you should pass new status in JSON format.
                                        There are only three available User's status: Online, Offline, Away;

