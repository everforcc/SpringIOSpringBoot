package cn.cc.lkl;

import cn.cc.lkl.dto.CommonRequestDTO;
import cn.cc.lkl.dto.CommonResponseDTO;
import cn.cc.lkl.dto.ReturnCode;

public class LKLTest {

    public static void main(String[] args) {

        String str = "请求响应失败：{\"code\":\"OP90001\",\"msg\":\"请求服务失败【无效请求(请求角色【AGENT】无访问权限，联系相关人员开通)】\"}";
        // 将str截取为json字符串
        String json = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
        System.out.println(json);

//        // 创建请求
//        CommonRequestDTO<UserRequest> request = new CommonRequestDTO<>();
//        request.setReqTime("20250414152108");
//        request.setReqData(userRequest);
//
//
//        // 创建成功响应
//        CommonResponseDTO<UserResponse> response = CommonResponseDTO.success(userResponse);
//
//
//        // 创建失败响应
//        CommonResponseDTO<UserResponse> errorResponse = CommonResponseDTO.fail(ReturnCode.PARAMETER_VALIDATION_FAILED);
    }

}
