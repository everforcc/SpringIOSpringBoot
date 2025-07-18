package cn.cc.netty.learn.mqtt.handler.impl;

import cn.cc.netty.learn.mqtt.mqtt.publish.MqttPublishService;
import cn.cc.netty.learn.mqtt.handler.HttpRequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class PublishHandler implements HttpRequestHandler {
    private final MqttPublishService publishService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PublishHandler(MqttPublishService publishService) {
        this.publishService = publishService;
    }

    @Override
    public boolean supports(FullHttpRequest req) {
        return req.method() == HttpMethod.POST && req.uri().startsWith("/publish");
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        String content = req.content().toString(CharsetUtil.UTF_8);
        String topic = "";
        String payload = "";
        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            topic = jsonNode.has("topic") ? jsonNode.get("topic").asText() : "";
            payload = jsonNode.has("payload") ? jsonNode.get("payload").asText() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = publishService.publish(topic, payload);
        String resp = "{\"success\":" + success + ",\"msg\":\"" + (success ? "消息已发送" : "发送失败") + "\"}";
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(resp, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response);
    }
} 