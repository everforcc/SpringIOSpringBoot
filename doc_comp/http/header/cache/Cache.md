<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 参考
- [CSDN](https://blog.csdn.net/u012375924/article/details/82806617)
- [HTTP缓存](https://imweb.io/topic/5795dcb6fb312541492eda8c)
- [WEB-DEV-HTTP缓存](https://web.dev/http-cache/) 
- [MDN-HTTP缓存](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching)
- [MDN-Cache-Control](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Cache-Control)
- [max-age优先级参考](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching)

---

我们用http访问时，会先发送一个请求，之后服务器返回一个应答，在Chrome的开发者工具(按F12或右击选择检查)中展现了整个过程：

- 第一部分General是概要，包含请求地址，请求方式，状态码，服务器地址以及Referrer 策略。
- 第二部分是应答头部，是服务器返回的。
- 第三部分是请求头部，是客户端发送的。



这次我们从两个角度来看看http的缓存：缓存控制和缓存校验。
- 缓存控制：控制缓存的开关，用于标识请求或访问中是否开启了缓存，使用了哪种缓存方式。
- 缓存校验：如何校验缓存，比如怎么定义缓存的有效期，怎么确保缓存是最新的。

### 一. 缓存控制 Cache-Control

#### 1. Pragma

Pragma有两个字段Pragma和Expires。Pragma的值为no-cache时，表示禁用缓存，Expires的值是一个GMT时间，表示该缓存的有效时间。

Pragma是旧产物，已经逐步抛弃，有些网站为了向下兼容还保留了这两个字段。如果一个报文中同时出现Pragma和Cache-Control时，以Pragma为准。同时出现Cache-Control和Expires时，以Cache-Control为准。即优先级从高到低是 
- Pragma -> Cache-Control -> Expires
- max-age > expires > last-modify

#### 2. Cache-Control

1. 符合缓存策略时，服务器不会发送新的资源，但不是说客户端和服务器就没有会话了，客户端还是会发请求到服务器的。
2. Cache-Control除了在响应中使用，在请求中也可以使用。我们用开发者工具来模拟下请求时带上Cache-Control：勾选Disable cache，刷新页面，可以看到Request Headers中有个字段Cache-Control: no-cache。
3. 同时在Response Headers中也能到Cache-Control字段，它的值是must-revalidate，这是服务端设置的。
4. **CDN** public和private的选择

- [字段可选值](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Cache-Control)
- nginx设置
~~~nginx.conf
server {
    listen 88;
    root /opt/ms;
    index index.php index.html index.htm index.nginx-debian.html;
    
    location ~* ^.+\.(css|js|txt|xml|swf|wav)$ {
        add_header Cache-Control no-store;
        add_header Cache-Control max-age=3600;
        add_header Cache-Control public;
        add_header Cache-Control only-if-cached;
        add_header Cache-Control no-cache;
        add_header Cache-Control must-revalidate;
    }
}

~~~
- no-store优先级最高

可缓存性 | value
---|---
no-store | 缓存不应存储有关客户端请求或服务器响应的任何内容，即不使用任何缓存。
no-cache| 在发布缓存副本之前，强制要求缓存把请求提交给原始服务器进行验证(协商缓存验证)。
public | 表明响应可以被任何对象（包括：发送请求的客户端，代理服务器，等等）缓存，即使是通常不可缓存的内容。（例如：1.该响应没有max-age指令或Expires消息头；2. 该响应对应的请求方法是 POST 。）
private | 表明响应只能被单个用户缓存，不能作为共享缓存（即代理服务器不能缓存它）。私有缓存可以缓存响应内容，比如：对应用户的本地浏览器。

5. 如何设置cache-control

~~~nginx.conf
location ~* ^.+\.(ico|gif|jpg|jpeg|png)$ { 
            expires      30d;
	}
~~~

这个指令等同于cache-control: max-age=2592000，同时你会在响应头部看到一个etag字段，这是由于nginx默认开启，如果要关闭可以增加个配置etag off。这个etag就是我们接下要看的缓存校验字段。

### 二. 缓存校验 Last-Modified 和 etag

在缓存中，我们需要一个机制来验证缓存是否有效。比如服务器的资源更新了，客户端需要及时刷新缓存；又或者客户端的资源过了有效期，但服务器上的资源还是旧的，此时并不需要重新发送。缓存校验就是用来解决这些问题的，在http 1.1 中，我们主要关注下Last-Modified 和 etag 这两个字段。

#### 1. Last-Modified

服务端在返回资源时，会将该资源的最后更改时间通过Last-Modified字段返回给客户端。客户端下次请求时通过If-Modified-Since或者If-Unmodified-Since带上Last-Modified，服务端检查该时间是否与服务器的最后修改时间一致：如果一致，则返回304状态码，不返回资源；如果不一致则返回200和修改后的资源，并带上新的时间。

If-Modified-Since和If-Unmodified-Since的区别是：
- If-Modified-Since：告诉服务器如果时间一致，返回状态码304
- If-Unmodified-Since：告诉服务器如果时间不一致，返回状态码412

#### 2. etag

- 单纯的以修改时间来判断还是有缺陷，比如文件的最后修改时间变了，但内容没变。对于这样的情况，我们可以使用etag来处理。
- etag的方式是这样：服务器通过某个算法对资源进行计算，取得一串值(类似于文件的md5值)，之后将该值通过etag返回给客户端，客户端下次请求时通过If-None-Match或If-Match带上该值，服务器对该值进行对比校验：如果一致则不要返回资源。

If-None-Match和If-Match的区别是：
- If-None-Match：告诉服务器如果一致，返回状态码304，不一致则返回资源
- If-Match：告诉服务器如果不一致，返回状态码412

### 三. 总结

1. 缓存开关是： pragma， cache-control。
2. 缓存校验有：Expires，Last-Modified，etag。

</span>