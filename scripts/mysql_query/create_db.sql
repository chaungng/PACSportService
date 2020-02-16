CREATE USER 'pacsport-admin''@'%' IDENTIFIED BY 'user_password';
CREATE DATABASE pacsport;
GRANT ALL PRIVILEGES ON pacsport.* TO 'pacsport-admin''@'%';
USE DATABASE pacsport;

/*
 * issue need to open port 3306
 * https://askubuntu.com/questions/272077/port-3306-appears-to-be-closed-on-my-ubuntu-server
*/

CREATE TABLE users (id VARCHAR(100), email VARCHAR(1000), password VARCHAR(128), first_name VARCHAR(1000), last_name VARCHAR(1000), birthday VARCHAR(1000));