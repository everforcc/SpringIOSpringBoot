<font face="Simsun" size=3>

key | value
---|---
200 | 成功，本地无缓存或失效，直接去服务器下载最新数据
304 | last-modified/etag控制，当下一层失效或者刷新时，服务器无变化则返回304
200(from cache) | 由expires/cache-control控制<br>1. expire是绝对时间<br>2. cache-control有效时间，两者都存在时cache-control覆盖expires只要没有失效，浏览器访问自己的缓存


</font>