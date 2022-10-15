<span  style="font-family: Simsun,serif; font-size: 17px; ">

~~~
docker pull fauria/vsftpd

docker run -d -p 20:20 -p 21:21 -p 21100-21110:21100-21110 -v /home/everforcc/docker/ftp/data/ftp:/home/everforcc/docker/ftp/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.c.c. -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 --name cc-vsftpd --restart=always fauria/vsftpd
~~~

### 三. ftp

- [文档](https://hub.docker.com/r/fauria/vsftpd)

~~~
# 1. 下载镜像 
docker pull fauria/vsftpd
# 2. 运行
docker run -d -p 21:21 -p 9200:20  -p 9210-9220:21100-21110 -v /home/everforcc/docker/ftp/data:/home/everforcc/docker/ftp/vsftpd -v /home/everforcc/docker/ftp/log:/var/log/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.c.c. -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=9210 -e PASV_MAX_PORT=9220 --name cc-vsftpd --restart=always fauria/vsftpd 
docker run -d -p 9201:21 -p 9200:20  -p 21100-21110:21100-21110 -v /home/everforcc/docker/ftp/data:/home/everforcc/docker/ftp/vsftpd -v /home/everforcc/docker/ftp/log:/var/log/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.c.c. -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 --name cc-vsftpd --restart=always fauria/vsftpd
docker run -d -p 9201:21 -p 9200:20  -p 9210-9220:21100-21110 -v /home/everforcc/docker/ftp/data:/home/everforcc/docker/ftp/vsftpd -v /home/everforcc/docker/ftp/log:/var/log/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.c.c. -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=9210 -e PASV_MAX_PORT=9220 --name cc-vsftpd --restart=always fauria/vsftpd
# 3. windows文件夹
ftp://everforcc:c.c.c.c.@150.158.12.83:21/
ftp://everforcc:c.c.c.c.@150.158.12.83:20/
ftp://everforcc:c.c.c.c.@150.158.12.83:9201/
ftp://everforcc:c.c.c.c.@150.158.12.83/
ftp://everforcc:c.c.5664@180.76.156.43/

# 或者随后在容器内创建,详细的看官方文档
# vi /etc/vsftpd/virtual_users.txt
~~~

</span>