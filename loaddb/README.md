Small loaddb without Springboot to test mysql

DOCKER EXECUTION
cd C:\Users\sergi\eclipse-workspace\loaddb

docker volume prune
docker volume create db-data

1.
docker build -t loaddb .
docker run -d --name loaddb loaddb
docker container ps

2.
docker-compose build
docker-compose up

docker exec -it mysql-demo mysql -p
show databases;
show tables;
drop database demo;