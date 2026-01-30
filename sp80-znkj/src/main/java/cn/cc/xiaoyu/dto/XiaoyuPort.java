package cn.cc.xiaoyu.dto;

import lombok.Data;

@Data
public class XiaoyuPort {

    private int num;
    // 0 空闲 1使用 2 故障
    private int type;



    public XiaoyuPort(int num) {
        this.num = num;
        this.type = 0;
    }

    public XiaoyuPort(int num, int type) {
        this.num = num;
        this.type = type;
    }

    public XiaoyuPort() {
    }
}