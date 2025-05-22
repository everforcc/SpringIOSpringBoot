package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 空调类，用于管理空调系统的状态和参数
@Data // 使用Lombok的@Data注解
public class AirConditioner {
    // 压缩机转速
    private String compressorSpeed;
    // 压力传感器值
    private String pressureSensor;
    // 冷凝器风扇速度
    private String condenserFanSpeed;
    // 冷凝器进口湿度
    private String condenserInletHumidity;
    // 冷凝器出口温度
    private String condenserOutletTemperature;
    // 膨胀阀状态
    private String expansionValveStatus;
    // 蒸发器风扇速度
    private String evaporatorFanSpeed;
    // 蒸发器进口湿度
    private String evaporatorInletHumidity;
    // 蒸发器出口温度
    private String evaporatorOutletTemperature;

}
