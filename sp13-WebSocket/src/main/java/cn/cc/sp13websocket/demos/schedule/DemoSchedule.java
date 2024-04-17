package cn.cc.sp13websocket.demos.schedule;

import cn.cc.sp13websocket.demos.utils.MessageMap;
import lombok.extern.slf4j.Slf4j;
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

    @Scheduled(cron = "0/5 * * * * ?")
    public void demoMethod() {
        log.info("new Date().toString(): {}", new Date().toString());
        Map<String, Session> onlineSessionClientMap = MessageMap.getSessionMap();
        log.info("onlineSessionClientMap.size(): {}", onlineSessionClientMap.size());

        MessageMap.getSessionMap().forEach((onlineSid, toSession) -> {
            String message = onlineSid + " :当前时间是: " + new Date().toString();
            log.info("服务端给客户端群发消息 ==> sid = {}, toSid = {}, message = {}", "schedule", onlineSid, message);
            toSession.getAsyncRemote().sendText(message);
        });
    }

}
