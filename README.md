# TestCase
This is server part of RESTful web-application. Working with HTTP responses and JSON.
It's working with Postgresql DB, there are  scripts to initialize DB in resourses folder: schema.sql (to create databse and table) and data.sql (to settle table).
Also there is postman collection of requests in postmanCollection folder to test application.

You could get User by GET requst, you could add new User by POST request and you could update User's status by PATCH requset. User's "Online" status has expire time - 5 minutes, 
if you don't update it in time it becomes "Away".



Here is requests:

GET: http://localhost:8080/users/1  - to getting userinfo with id = 1;

POST: http://localhost:8080/users/  - to adding new User to DB, you should pass User in JSON format;

PATCH: http://localhost:8080/users/1?status=Online  - to update User's status. There are only three available User's status: Online, Offline, Away;

