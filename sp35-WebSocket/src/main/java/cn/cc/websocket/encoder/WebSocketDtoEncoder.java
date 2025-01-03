package cn.cc.websocket.encoder;

import cn.cc.websocket.dto.WebSocketDto;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 将要发送的对象编码为txt
 * WebSocketDtoDecoder
 */
public class WebSocketDtoEncoder implements Encoder.Text<WebSocketDto> {

    @Override
    public String encode(WebSocketDto object) throws EncodeException {
        return JSONObject.toJSONString(object);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
