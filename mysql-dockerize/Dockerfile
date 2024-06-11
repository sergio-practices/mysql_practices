FROM openjdk:21
WORKDIR /app
COPY ./target/mysql-test.jar /app
EXPOSE 8080
CMD ["java", "-jar", "mysql-test.jar"]