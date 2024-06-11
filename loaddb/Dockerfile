FROM mysql:8.0.30

RUN chown -R mysql:root /var/lib/mysql/
COPY init_01.sql /etc/mysql/init_01.sql
RUN cp /etc/mysql/init_01.sql /docker-entrypoint-initdb.d

EXPOSE 3306