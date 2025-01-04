package cn.cc.websocket.schedule;

import cn.cc.websocket.dto.WebSocketDto;
import cn.cc.websocket.utils.MessageMap;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @Author everforcc
 * @Date 2024-04-17 21:54
 * Copyright
 */
@Slf4j
@Component
public class DemoSchedule {

    @Autowired
    RedissonClient redissonClient;

    @Value("${server.port}")
    String port;

    /**
     * 尝试把 Session 存到redis但是不行，因为不能序列化
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void demoMethod() {
        log.info("redissonClient.getRemoteService().toString(): {}", redissonClient.getRemoteService().toString());
        log.info("new Date().toString(): {}", new Date().toString());

        Map<String, Session> onlineSessionClientMap = null;
        if ("map".equals(MessageMap.type)) {
            onlineSessionClientMap = MessageMap.getSessionMap();
            log.info("onlineSessionClientMap.size(): {}", onlineSessionClientMap.size());
        } else {
            final RBucket<Map<String, Session>> imapRBucket = redissonClient.getBucket(MessageMap.BUSI_TEST);
            if (imapRBucket.isExists()) {
                onlineSessionClientMap = imapRBucket.get();
            }
        }

        onlineSessionClientMap.forEach((onlineSid, toSession) -> {
            String message = onlineSid + " :当前时间是: " + new Date().toString();
            log.info("服务端: {} 给客户端群发消息 ==> sid = {}, toSid = {}, message = {}", port, "schedule", onlineSid, message);
//            toSession.getAsyncRemote().sendText(message);
            WebSocketDto webSocketDto = new WebSocketDto();
            webSocketDto.setString(message);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("k", "v");
            jsonObject.put("k2", 233);
            webSocketDto.setJsonObject(jsonObject);
            toSession.getAsyncRemote().sendObject(webSocketDto);
        });
    }

}
