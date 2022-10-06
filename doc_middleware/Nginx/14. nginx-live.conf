
worker_processes auto;

rtmp_auto_push on;

events { }

#直播流配置

rtmp {

  server {

    #端口

    listen 1935;

    # RTMP 直播流配置

    application rtmplive {

      # 非常重要， 设定让ngnix断开阻塞中的连接， 才能触发exec_record_done

      # 以及客户端的LFLiveKit reconnectCount自动重新连接才会好用

      drop_idle_publisher 5s;

      live on;

    }

    # HLS 直播流配置

    application hls {

      live on;

      record all;

      record_path /tmp;

      record_max_size 10485760K;

      record_unique off;

      record_append on;

      hls on;

      hls_path /tmp;

      hls_fragment 5s;

    }

  }

}

# HLS 拉流配置（vlc播放 http://127.0.0.1:8080/hls/XXX.m3u8，其中http://127.0.0.1:8080/hls/为下面location的值，XXX为具体推流的配置）

http {

  server {

    listen 8080;

    location /hls {

      types {

        application /vnd.apple.mpegurl m3u8;

        video /mp2t ts;

      }

      #访问权限开启，否则访问这个地址会报403

      autoindex on;

      #视频流存放地址，与上面的hls_path相对应，这里root和alias的区别可自行百度

      alias /tmp;

      expires -1;

      add_header Cache-Control no-cache;

      #防止跨域问题

      add_header 'Access-Control-Allow-Origin' '*';

      add_header 'Access-Control-Allow-Credentials' 'true';

      add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';

      add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type';

    }

  }

}
