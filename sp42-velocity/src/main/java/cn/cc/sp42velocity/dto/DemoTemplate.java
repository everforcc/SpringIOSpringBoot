package cn.cc.sp42velocity.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @Description : 示例模板
 * @Author : GKL
 * @Date: 2024-05-21 09:57
 */
@Data
public class DemoTemplate implements Serializable {
    private String id;
    private String time;
    private List<ListDemo> listDemo;
}