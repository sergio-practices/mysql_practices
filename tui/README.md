## Summary

TUI DX Backend technical Test v2

COMMANDS FOR WINDOWS
--AUTH
curl --location http://localhost:8080/private/orders/SP_000001 -u spf:123
curl --location http://localhost:8080/private/orders?firstName=S -u spf:123
--PUBLIC
curl -X POST http://localhost:8080/public/order -H "Content-Type:application/json" -d "{ \"deliveryAddressId\": 1, \"pilotes\": 5 }"
curl -X PUT http://localhost:8080/public/order -H "Content-Type:application/json" -d "{ \"code\": \"SP_000001\", \"deliveryAddressId\": 1, \"pilotes\": 5 }"
--TIME ERROR
curl -X PUT http://localhost:8080/public/order -H "Content-Type:application/json" -d "{ \"code\": \"SP_000004\", \"deliveryAddressId\": 1, \"pilotes\": 5 }"
--ASYNC
curl --location http://localhost:8080/async/stats

DB RELATIONS
 ____________           ____________           ____________
|            |   1:1   |            |   1:N   |            |
| PURCHASES  |-|-----|-|  ADRESSES  |-|------>|  CLIENTS   |
|____________|         |____________|         |____________|  

All tables are filled during the load in H2, but the only table we modify is PURCHASES

DOCKER EXECUTION
cd ..\tui-test-v2-master
1.
mvn clean install
docker build -t tui .
docker run -d -p 8080:8080 --name tui tui
docker container ps

2.
docker-compose build
docker-compose up

Created bundle 
git bundle create TUI.bundle --all –branches
git clone TUI.bundle tui-test-TUI

Execute MainApplication.java to run the project

URL SWAGGER
http://localhost:8080/swagger-ui/index.html

URL H2 CONSOLE
http://localhost:8080/h2-console
jdbc:h2:mem:testdb
user: sa
password:

#to debug
docker-compose -f docker-compose-debug.yml up

#SONAR
mvn clean verify sonar:sonar -Dsonar.projectKey=test -Dsonar.projectName='test' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_f0f8>c2f5bd2b2d5e8a71f6486c3cfca9f4537ab4