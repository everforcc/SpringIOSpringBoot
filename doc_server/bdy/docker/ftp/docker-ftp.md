<font face="Simsun" size=3>

~~~
docker pull fauria/vsftpd

docker run -d -p 20:20 -p 21:21 -p 21100-21110:21100-21110 -v /home/data/ftp:/home/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.5664 -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 --name cc-vsftpd --restart=always fauria/vsftpd
~~~

</font>