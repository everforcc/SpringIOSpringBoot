<span  style="font-family: Simsun,serif; font-size: 17px; ">

## 合并手机下载的视频片段,其他的同理

### 1. 手机文件压缩,传到windows,

- 文件碎片太多传的慢

### 2. 解压,将文件路径放到一个清单内

- 自定义一个文件夹,不要随便放

### 3. 当前文件跟目录

~~~
E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\concatfile
~~~~

### 4. 目录内视频文件夹

- 数字为视频分片
~~~
104e476d2be01cefa4a08d7728ac6487/
                                1
                                2
                                3
                                
2569c9abc8eb570b698d7f6b0a25d171/
                                1
                                2
                                3
~~~

### 5. 合成同名文件清单

~~~
104e476d2be01cefa4a08d7728ac6487.txt
2569c9abc8eb570b698d7f6b0a25d171.txt
~~~

### 6. 文件内容格式

~~~
file 'E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\concatfile\104e476d2be01cefa4a08d7728ac6487\0'
file 'E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\concatfile\104e476d2be01cefa4a08d7728ac6487\1'
file 'E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\concatfile\104e476d2be01cefa4a08d7728ac6487\2'
file 'E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\concatfile\104e476d2be01cefa4a08d7728ac6487\3'
~~~

### 7. 生成视频命名

~~~
104e476d2be01cefa4a08d7728ac6487.txt.mp4
2569c9abc8eb570b698d7f6b0a25d171.txt.mp4
~~~

### 8. 执行脚本

~~~
## 1.清单的路径 2.目标文件的路径
ffmpeg -f concat -safe 0 -i "%s" -c copy "%s"
~~~

### 9. 测试代码

~~~
ConcatTXTFileToVideoTests.java
~~~

</span>