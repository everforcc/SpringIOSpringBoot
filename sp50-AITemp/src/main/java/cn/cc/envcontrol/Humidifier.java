package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 加湿器类，用于管理加湿系统的状态和参数
@Data // 使用Lombok的@Data注解
public class Humidifier {

    // 加湿器状态
    private String humidifierStatus;
    // 臭氧发生器状态
    private String ozoneGeneratorStatus;
    // 加湿器水箱状态
    private String waterTankStatus;
    // 水泵状态
    private String pumpStatus;

}
