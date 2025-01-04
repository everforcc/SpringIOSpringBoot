package cn.cc.websocket.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class WebSocketDto implements Serializable {

    private String string;

    private JSONObject jsonObject;

}
