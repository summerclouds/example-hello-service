CREATE USER IF NOT EXISTS myuser@'%' IDENTIFIED BY 'my_cool_secret';
SET PASSWORD FOR myuser@'%' = PASSWORD('my_cool_secret');
CREATE DATABASE test;
GRANT ALL ON test.* TO myuser@'%';