<span  style="font-family: Simsun,serif; font-size: 17px; ">

key | value
---|---
If-Modified-Since | 告诉服务器如果时间一致，返回状态码304
If-Unmodified-Since | 告诉服务器如果时间不一致，返回状态码412
If-None-Match | 告诉服务器如果一致，返回状态码304，不一致则返回资源
If-Match | 告诉服务器如果不一致，返回状态码412

</span>