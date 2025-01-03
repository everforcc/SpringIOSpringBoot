package cn.cc.websocket.encoder;

import cn.cc.websocket.dto.WebSocketDto;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WebSocketDtoDecoder implements Decoder.Text<WebSocketDto> {
    @Override
    public WebSocketDto decode(String s) throws DecodeException {
        return JSONObject.parseObject(s, WebSocketDto.class);
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
