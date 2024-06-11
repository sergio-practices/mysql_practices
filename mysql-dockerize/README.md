docker network rm my-network
docker network create my-network

docker rm -f mysql-db
docker volume prune
docker volume create mysql-db-data
docker volume ls
docker run -d -p 33060:3306 --net=my-network --name=mysql-db  -e MYSQL_ROOT_PASSWORD=secret --mount src=mysql-db-data,dst=/var/lib/mysql mysql

docker exec -it mysql-db mysql -p
create database demo;
use demo;
CREATE TABLE USER (ID INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100), PRIMARY KEY(ID));

DOCKER EXECUTION
cd ..\mysql
1.
mvn clean install
docker build -t mysql-test .
docker run -d -p 8080:8080 --net=my-network --name mysql-test mysql-test
docker container ps