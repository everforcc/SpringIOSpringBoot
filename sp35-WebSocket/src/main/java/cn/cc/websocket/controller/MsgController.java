package cn.cc.websocket.controller;

import cn.cc.core.domain.R;
import cn.cc.websocket.utils.MessageMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/msg")
public class MsgController {

    /**
     * @return 当前客户端编号 list
     */
    @GetMapping("/list")
    public R<Set<String>> list() {
        return R.ok(MessageMap.getSessionMap().keySet());
    }

}
