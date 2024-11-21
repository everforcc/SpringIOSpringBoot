package baidu.ocr;
import com.baidu.aip.ocr.AipOcr;
import cn.cc.utils.commons.io.RFileUtils;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {

    private static String content;

    /**
     *
     * @param path 文件路径
     * @param fileName 文件名
     */
    public static void flow(String path,String fileName){
        // 1. 获得AipOcr
        AipOcr client = getAipOcr();

        //getJSON(a);
        // 2.调用接口
        String content = sample(client,path);

        System.out.println("content: " + content);
        // 3. 处理数据
        //String result =  getJSON(content);

        String imgCharResultPath = String.format(BDConstant.imgCharPath,fileName);
        RFileUtils.writeStringToFile(imgCharResultPath,content);
    }

    private static AipOcr getAipOcr(){
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(BDConstant.APP_ID, BDConstant.API_KEY, BDConstant.SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // ??? 不懂
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "/resources/log4j2.properties");
        return client;
    }

    /**
     *
     * @param client 客户端
     * @param imgPath 图片本地地址
     * @return
     */
    private static String sample(AipOcr client,String imgPath){

        // 查询参数: 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        // 参数为本地图片路径
        JSONObject res = client.basicGeneral(imgPath, options);
        // 参数为本地图片二进制数组
//        byte[] file = readImageFile(image);
//        res = client.basicGeneral(file, options);
        // 通用文字识别, 图片参数为远程url图片
//        String url="";
//        JSONObject res = client.basicGeneralUrl(url, options);

        content = res.toString(2);

        System.out.println(content);

        /*SaveToFile saveToFile=new SaveToFile(ConstantFile.javaFilePath + "/test/OCRmodel","2.txt",content);
        saveToFile.save();*/
        //

        System.out.println(res.toString(2));

        return content;
    }


    /**
     * 处理返回的json
     * @param
     * @return
     * @throws Exception
     */
//    private static String getJSON(String string){
//
//        System.out.println(string);
//
//        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(string);
//        JSONArray jsonArray = jsonObject.getJSONArray("words_result");
//        com.alibaba.fastjson.JSONObject jsonObject1;
//
//        System.out.println("获得分词数量:"+jsonArray.size());
//        StringBuffer stringBuffer=new StringBuffer();
//        for(int i=0;i<jsonArray.size();i++){
//            jsonObject1 = (com.alibaba.fastjson.JSONObject)jsonArray.get(i);
//            //stringBuffer.append((net.sf.json.JSONObject) ((net.sf.json.JSONObject) jsonArray.get(0)).get("words"));
//            stringBuffer.append(jsonObject1.get("words"));
//        }
//        //System.out.println(stringBuffer.toString());
//
//        return stringBuffer.toString();
//    }

    public static void main(String[] args) throws Exception {
        flow(BDConstant.charPath,"2546160883");
    }

}