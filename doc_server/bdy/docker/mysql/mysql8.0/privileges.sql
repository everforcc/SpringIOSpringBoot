use mysql;
select host, user from user;
CREATE USER 'cceverfor'@'%' IDENTIFIED BY '5664c.c.';
ALTER USER 'cceverfor'@'%' IDENTIFIED WITH mysql_native_password BY '5664c.c.';
GRANT ALL PRIVILEGES ON `oneforall`.* TO 'cceverfor'@'%' ;
flush privileges;