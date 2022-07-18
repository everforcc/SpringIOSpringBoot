<span  style="font-family: Simsun,serif; font-size: 17px; ">

## 合并手机下载的m3u8,其他的同理

### 1. 将文件压缩,copy压缩包到windows,解压

-

### 2. 当前解压路径为

~~~
例如:
E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\m3u8
~~~

### 3. 该文件夹内

~~~
视频1.m3u8

视频1/1
    /2
    /3
    /99
~~~

### 4. m3u8 旧内容

~~~m3u8
#EXTM3U
#EXT-X-VERSION:3
#EXT-X-TARGETDURATION:6
#EXT-X-PLAYLIST-TYPE:VOD
#EXT-X-MEDIA-SEQUENCE:0
#EXT-X-KEY:METHOD=AES-128,URI="file:///sdcard/Quark/Download/视频1/0.key"
#EXTINF:3,
file:///sdcard/Quark/Download/视频1/0
#EXTINF:3,
file:///sdcard/Quark/Download/视频1/1
#EXTINF:3,
file:///sdcard/Quark/Download/视频1/2
#EXTINF:3,
file:///sdcard/Quark/Download/视频1/3
#EXT-X-ENDLIST
~~~

### 5. 新 m3u8

- 命名
- 文件命名前面加个 new-

~~~
new-视频1.m3u8
~~~

- 内容
- windows的 必须以下面的格式

~~~
盘符:\\路径/路径/ 
注意盘符后为两个反斜杠,后面的为一个正斜杠
E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8
~~~

- 内容示例

~~~m3u8
#EXTM3U
#EXT-X-VERSION:3
#EXT-X-TARGETDURATION:6
#EXT-X-PLAYLIST-TYPE:VOD
#EXT-X-MEDIA-SEQUENCE:0
#EXT-X-KEY:METHOD=AES-128,URI="E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8/视频1/0.key"
#EXTINF:3,
E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8/视频1/0
#EXTINF:3,
E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8/视频1/1
#EXTINF:3,
E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8/视频1/2
#EXTINF:3,
E:\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8/视频1/3
#EXT-X-ENDLIST
~~~

### 6. 视频命名

- 在新m3u8后追加 .mp4

~~~
new-视频1.m3u8.mp4
~~~

### 7. 执行脚本

~~~
### m3u8 转码mp4 1.第一个参数 m3u8 的位置， 2.第二个新文件的位置
ffmpeg -allowed_extensions ALL -protocol_whitelist "file,http,crypto,tcp" -i "%s" -c copy "%s"
~~~

### 8. 测试代码

~~~
ConcatM3U8ToVideoTests.java
~~~

</span>