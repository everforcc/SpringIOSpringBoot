package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 新风类，用于管理新风系统的状态和参数
@Data // 使用Lombok的@Data注解
public class NewAir {
    // 风扇1转速
    private String fanSpeed;
    // 风扇2转速
//    private String fan2Speed;
    // 颗粒物传感器数值
    private double particulateMatterValue;
    // 新风滤芯状态
    private String newAirFilterStatus;

}
