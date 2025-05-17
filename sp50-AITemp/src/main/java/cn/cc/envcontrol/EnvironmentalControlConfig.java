package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 环境控制配置类，用于管理环控系统的配置参数
@Data // 使用Lombok的@Data注解
public class EnvironmentalControlConfig {
    // 新风温度阈值
    private double newWindTemperatureThreshold;
    // 新风湿度低阈值
    private double newWindHumidityThresholdLow;
    // 新风湿度高阈值
    private double newWindHumidityThresholdHigh;

    // 空调开关
    private String airConditionerStatus;
    // 新风开关
    private String newWindStatus;
    // 加湿开关
    private String humidifierStatus;

    // 柜内温度
    private double cabinetTemperature;
    // 柜内湿度
    private double cabinetHumidity;

}
