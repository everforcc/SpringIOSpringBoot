package cn.cc.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket的操作类
 */
@Component
@Slf4j
@ServerEndpoint(value = "/websocket2")
public class WebSocketNoParamsServer {

    @OnOpen
    public void onOpen(Session session) {
        log.info("连接建立中 ==> session_id = {}", session.getId());
        log.info("开始监听新连接：session_id = {}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        log.info("==> 关闭该连接信息：session_id = {}", session.getId());
    }

    /**
     * 收到客户端消息后调用的方法。由前端<code>socket.send</code>触发
     * * 当服务端执行toSession.getAsyncRemote().sendText(xxx)后，前端的socket.onmessage得到监听。
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端消息 ==> message = {}", message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        error.printStackTrace();
    }

}
