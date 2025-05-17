package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 环境类，用于管理室内和室外的环境参数（温度和湿度）
@Data // 使用Lombok的@Data注解
public class Environment {
    // 室内温度
    private double indoorTemperature;
    // 室内湿度
    private double indoorHumidity;
    // 室外温度
    private double outdoorTemperature;
    // 室外湿度
    private double outdoorHumidity;

}
