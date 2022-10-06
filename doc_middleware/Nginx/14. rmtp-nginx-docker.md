<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [tiangolo/nginx-rtmp](https://hub.docker.com/r/tiangolo/nginx-rtmp)

[TOC]

### 2.1. dockerfile

- Dockerfile
~~~
FROM tiangolo/nginx-rtmp:latest-2021-09-17
 
COPY nginx.conf  /etc/nginx/nginx.conf
~~~

### 2.2. nginx.conf

~~~
# 参考 nginx-live.conf
# 使用的时候注意切换linux编码
~~~

### 2.3. 制作镜像

~~~
docker build -t trc-nginx-rtmp .
~~~

### 2.4 查看镜像

~~~
docker images | grep trc-nginx-rtmp
~~~

### 3. 运行

~~~
docker run -d -p 1935:1935 -p 1900:8080 -v /Users/taoruicheng/temp:/tmp --privileged=true --name trc-nginx-rtmp trc-nginx-rtmp
~~~
- 
~~~
-v /Users/taoruicheng/temp:/tmp 的意思是把容器内部的/tmp文件夹 映射到外部/Users/taoruicheng/temp文件夹。这样在容器外部就能看到视频直播的文件了。特别需要注意/Users/taoruicheng/temp文件夹的权限设置成777。
注意这里面开启了1935推流端口 和 1900拉流端口。
~~~

### 4. ffmpeg推流

1. rtsp
~~~
ffmpeg -rtsp_transport tcp -i rtsp://admin:root@11.122.2.143:554 -vcodec copy -f flv -an rtmp://11.122.2.143:1935/hls/abc

(vlc播放地址：http://11.122.2.143:1900/hls/abc.m3u8)
~~~
2. 本地文件
~~~
ffmpeg  -re -stream_loop -1 -i /Users/taoruicheng/工作资料目录/视频素材/仓xx.mp4 -vcodec copy -acodec copy -f flv  rtmp://11.122.2.143:1935/hls/xxx

(vlc播放地址：http://11.122.2.143:1900/hls/xxx.m3u8)
~~~

### 5. html

~~~

~~~

### 6. obs vlc

- obs
~~~
1. 设置推流

2. 
~~~
- [VLC-下载](https://www.videolan.org/)

</span>