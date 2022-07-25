<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [Range](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Range_requests)

~~~
Range: bytes=0-1023
~~~


Accept-Ranges:

例：

Accept-Ranges:bytes

表示服务器支持http中的Range功能，能够分段请求客户端能够分段请求服务器。

我们上网时常用的“断点续传”，或者服务器所谓的“多线程下载”就是靠的服务器端的Range技术。

Range功能的请求-响应流程如此：

客户端发起带range的请求：

~~~
GET  /test.rar  HTTP/1.1
Connection:  close
Host:  116.1.219.219
Range:  bytes=0-100
~~~

在头中添加Range字段，表示我要请求[0-100]这101个字节的数据。

此处Range的值，可以添加多个片段，如 Range：bytes=0-100,200-300等。

服务器响应报文：

~~~
HTTP/1.1 206 OK
Content-Length:  801     
Content-Type:  application/octet-stream 
Content-Location: http://www.onlinedown.net/hj_index.htm
Content-Range:  bytes  0-100/2350        //2350:文件总大小
Last-Modified: Mon, 16 Feb 2009 16:10:12 GMT
Accept-Ranges: bytes
ETag: "d67a4bc5190c91:512"
Date: Wed, 18 Feb 2009 07:55:26 GMT
~~~

---

~~~java
// 这个可以实现分段下载，后续我再重新合成
conn.setRequestProperty("Range", "bytes=0-10"); 
// 多个文件合成新的文件 第二个参数表示追加写入
FileOutputStream fo = new FileOutputStream(file,true);
~~~

</span>