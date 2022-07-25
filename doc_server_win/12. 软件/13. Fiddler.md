<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [官网](https://www.telerik.com/fiddler)
- [下载地址](https://www.telerik.com/download/fiddler)
- [教程](https://www.trinea.cn/android/android-network-sniffer/)
- 教程测试过可以
- 但是某些软件还是不习惯，比如米游社

--- 

### Android利用Fiddler进行网络数据抓包

- 主要介绍Android及IPhone手机上如何利用Fiddler进行网络数据抓包，比如我们想抓某个应用(微博、微信、墨迹天气)的网络通信请求就可以利用这个方法。
- Mac 下请使用 Charles 代替 Fiddler。
- 相对于tcpdump配合wireshark抓包的优势在于：(1)无需root (2)对Android和Iphone同样适用 (3)操作更简单方便(第一次安装配置，第二次只需设置代理即可) (4)数据包的查看更清晰易懂,Fiddler的UI更简单明了 (5) 可以查看https请求。如果你坚持使用tcpdump也可见：利用tcpdump和wireshark抓取网络数据包。

**PS：需要1台PC做辅助，且PC需要与手机在同一局域网内或有独立公网ip**

#### 1、PC端安装Fiddler

下载地址：Fiddler.exe，下面是Fiddler的简单介绍(不感兴趣的可以直接跳过)：

Fiddler是强大且好用的Web调试工具之一，它能记录客户端和服务器的http和https请求，允许你监视，设置断点，甚至修改输入输出数据，Fiddler包含了一个强大的基于事件脚本的子系统，并且能使用.net语言进行扩展，在web开发和调优中经常配合firebug使用。

Fiddler的运行机制其实就是本机上监听8888端口的HTTP代理。 对于PC端Fiddler启动的时候默认IE的代理设为了127.0.0.1:8888，而其他浏览器是需要手动设置的，所以如果需要监听PC端Chrome网络请求，将其代理改为127.0.0.1:8888就可以监听数据了，手机端按照下面的设置即可完成整个系统的http代理。


#### 2、 配置PC端Fiddler和手机

##### (1) 配置Fiddler允许监听https

打开Fiddler菜单项Tools->Fiddler Options，选中decrypt https traffic和ignore server certificate errors两项，如下图：

- ![01](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011350619.jpeg)

第一次会提示是否信任fiddler证书及安全提醒，选择yes，之后也可以在系统的证书管理中进行管理。

##### (2) 配置Fiddler允许远程连接

如上图的菜单中点击connections，选中allow remote computers to connect，默认监听端口为8888，若被占用也可以设置，配置好后需要重启Fiddler，如下图：

- ![02](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011351475.jpeg)

##### (3) 配置手机端

Pc端命令行ipconfig查看Fiddler所在机器ip，本机ip为10.0.4.37，如下图

- ![03](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011352029.jpeg)

打开手机连接到同一局域网的wifi，并修改该wifi网络详情(长按wifi选择->修改网络)->显示高级选项，选择手动代理设置，主机名填写Fiddler所在机器ip，端口填写Fiddler端口，默认8888，如下图：

- ![04](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011353359.jpeg)

这时，手机上的网络访问在Fiddler就可以查看了，如下图微博和微信的网络请求：

- ![05](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011354195.jpeg)

可以双击上图某一行网络请求，右侧会显示具体请求内容(Request Header)和返回内容(Response Header and Content)，如下图：

- ![06](https://gitee.com/MyYukino/media/raw/master/PicGo/202205011354049.jpeg)

- 可以发现Fiddler可以以各种格式查看网络请求返回的数据，包括Header, TextView(文字), ImageView(图片), HexView(十六进制)，WebView(网页形式), Auth(Proxy-Authenticate Header), Caching(Header cache), Cookies, Raw(原数据格式), JSON(json格式), XML(xml格式)很是方便。
- 停止网络监控的话去掉wifi的代理设置即可，否则Fiddler退出后手机就上不网了哦。
- 如果需要恢复手机无密码状态，Android端之后可以通过系统设置-安全-受信任的凭据-用户，点击证书进行删除或清除凭据删除所有用户证书，再设置密码为无。
- 如果只需要监控一个软件，可结合系统流量监控，关闭其他应用网络访问的权限。
- 对你有帮助的话，去知乎点个赞让更多人了解：如何在 Android 手机上实现抓包

</span>