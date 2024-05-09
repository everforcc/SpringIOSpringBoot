package cn.cc.mvc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description : 系统返回值
 * @Author : GKL
 * @Date: 2024-05-09 14:57
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;

    private Object msg;

    public static Result success(Object msg){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

}
