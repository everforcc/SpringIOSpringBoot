<span  style="font-family: Simsun,serif; font-size: 17px; ">

~~~
docker pull fauria/vsftpd

docker run -d -p 20:20 -p 21:21 -p 21100-21110:21100-21110 -v /home/data/ftp:/home/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.5664 -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 --name cc-vsftpd --restart=always fauria/vsftpd
~~~

### 三. ftp

- [文档](https://hub.docker.com/r/fauria/vsftpd)

~~~
# 1. 下载镜像 
docker pull fauria/vsftpd
# 2. 运行
docker run -d -p 20:20 -p 21:21 -p 21100-21110:21100-21110 -v /home/data/ftp/data:/home/vsftpd -v /home/data/ftp/log:/var/log/vsftpd -e FTP_USER=everforcc -e FTP_PASS=c.c.5664 -e PASV_ADDRESS=0.0.0.0 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 --name cc-vsftpd --restart=always fauria/vsftpd
# 3. windows文件夹
ftp://everforcc:c.c.5664@180.76.156.43/

# 或者随后在容器内创建,详细的看官方文档
# vi /etc/vsftpd/virtual_users.txt
~~~

</span>