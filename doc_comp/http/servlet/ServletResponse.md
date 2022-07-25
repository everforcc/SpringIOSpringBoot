<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 参考
- [cnblogs](https://www.cnblogs.com/mingforyou/p/4259113.html)

[TOC]

### 1. response.setHeader()的用法

### 1.1 http消息头

1. 通用请求头

即能用于请求消息中,也能用于响应信息中,但与被传输的实体内容没有关系的信息头,如Data,Pragma

主要: Cache-Control , Connection , Data , Pragma , Trailer , Transfer-Encoding , Upgrade

2. 请求头

用于在请求消息中向服务器传递附加信息,主要包括客户机可以接受的数据类型,压缩方法,语言,以及客户计算机上保留的信息和发出该请求的超链接源地址等.

主要: Accept , Accept-Encoding , Accept-Language , Host ,

3. 响应头

用于在响应消息中向客户端传递附加信息,包括服务程序的名称,要求客户端进行认证的方式,请求的资源已移动到新地址等.

主要: Location , Server , WWW-Authenticate(认证头)

4. 实体头

用做实体内容的元信息,描述了实体内容的属性,包括实体信息的类型,长度,压缩方法,最后一次修改的时间和数据的有效期等.

主要: Content-Encoding , Content-Language , Content-Length , Content-Location , Content-Type

5. 扩展头

主要：Refresh, Content-Disposition

### 1.2 几个主要的头的作用

1. Content-Type的作用

~~发送的数据类型~~

eg: 当Content-Type 的值设置为text/html和text/plain时,前者会让浏览器把接收到的实体内容以HTML格式解析,后者会让浏览器以普通文本解析.

2. Content-Disposition 的作用

**当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。**

- 需要处理中文乱码问题

3. Authorization头的作用

- Authorization的作用是当客户端访问受口令保护时，服务器端会发送401状态码和WWW-Authenticate响应头，要求客户机使用Authorization来应答。


### 1.3 文件下载

- 要实现文件下载，我们只需要设置两个特殊的相应头
- Content-Type:       application/octet-stream
- Content-Disposition: attachment;filename=aaa.zip
- 中文文件名乱码
~~~
MimeUtility.encodeWord("中文.txt");//现在版本的IE还不行
new String("中文".getBytes("GB2312"),"ISO8859- 1");//实际上这个是错误的
### 正确方式
new String( fileMsg.getBytes("gb2312"), "ISO8859-1" )
~~~
- 在确保附件文件名都是简体中文字的情况下，那么这个办法确实是最有效的，不用让客户逐个的升级IE。如果台湾同胞用，把gb2312改成big5就 行。但现在的系统通常都加入了 国际化的支持，普遍使用UTF-8。如果文件名中又有简体中文字，又有繁体中文，还有日文。那么乱码便产生了。另外，在上Firefox (v1.0-en)下载也是乱码。
- 


</span>