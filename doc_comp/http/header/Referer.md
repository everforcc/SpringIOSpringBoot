<span  style="font-family: Simsun,serif; font-size: 17px; ">

从主页上链接到一个朋友那里，他的服务器就能够从HTTP Referer中统计出每天有多少用户点击我主页上的链接访问他的网站。

Referer的正确英语拼法是referrer。由于早期HTTP规范的拼写错误，为了保持向后兼容就将错就错了。其它网络技术的规范企图修正此问题，使用正确拼法，所以拼法不统一。

Request.ServerVariables("HTTP_REFERER")的用法是防外连接。

</span>