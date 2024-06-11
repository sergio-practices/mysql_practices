docker rm -f mysql-db
docker volume prune
docker volume create mysql-db-data

docker run -d -p 33060:3306 --name=mysql-db  -e MYSQL_ROOT_PASSWORD=secret --mount src=mysql-db-data,dst=/var/lib/mysql mysql:8.1
docker exec -it mysql-db mysql -p
# create database demo;

mvn flyway:migrate

mysql> select * from flyway_schema_history;
+----------------+---------+--------------+------+------------------------+------------+--------------+---------------------+----------------+---------+
| installed_rank | version | description  | type | script                 | checksum   | installed_by | installed_on        | execution_time | success |
+----------------+---------+--------------+------+------------------------+------------+--------------+---------------------+----------------+---------+
|              1 | 0.1     | initial      | SQL  | V0_1__initial.sql      |  585012873 | root         | 2024-06-10 15:10:30 |             30 |       1 |
|              2 | 0.2     | initial data | SQL  | V0_2__initial_data.sql | 1187493069 | root         | 2024-06-10 15:10:30 |              8 |       1 |
+----------------+---------+--------------+------+------------------------+------------+--------------+---------------------+----------------+---------+