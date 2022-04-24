package com.cc.sp70craw.utils.idm;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class SubFile {

    public static void main(String[] args) {
        String url = "https://i0.hdslb.com/bfs/album/32fffb9d5fcedc536bdc8e16e213444bbc0847dc.jpg";
        String filename = "32fffb9d5fcedc536bdc8e16e213444bbc0847dc.jpg";
        //down("http://localhost:81/open/file/read/down/【480P】【秦时明月 第一季 百步飞剑】 第02话 隐姓埋名 【The Legend of Qin】.mkv");
        down(url,filename);
    }

    /**
     * 1. 获取总大小
     * 2. 根据要求分段
     * 3. 请求各个分段数据
     * 4. 合并
     */

    public static void down(String urlPath,String fileName){
        URL url = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json");
            //conn.setRequestProperty("token", "7a1620347169f7942aa59cda3a61eebd88d610630b9751a8445dfb4e69838707cdc8f7cf3e53cadddaf3014d75ecd5290a640d0167e2750cee4319a64c9a5acb2c21a45eac123acc3dca249efcc52963fdf16e7241593ef06bef4b326a6581904c1b4b85d1caec83b0714b7d9f63fa2bb96830488925f29b1e6953e84d9b217e791a56b96626d383711a1f00c46ee1e5a8da8308f07affca5336c064883ba7bcabce0f854b2adb8b98d34e873eb7ab56920e4aabf2b8051a9cb2e712bf53c0559bd1998c4d5820638073f58450cd841ba9d6330bb426b7475467424203376bd04fb7fedff1803858d3f676bd512d1a5e8888fac12cea9e789caaa7deed14739220c357e1c0ebe25e6b9b2fddc4895ef6a5a21cca691dcd05829686eb89b1d0018db91b617863ae53b326f86a58f70cbec807dd353d18f68ff9d2c9556c100f801788b80db614e3bf03961305f318cfb55873ed85ad62421fe220cd676b4ea6bb017c9ee5e9db6e87d32117936b080102fba98bdb590ac0a904a6833e24e55ca8b3351615bdbee8fbf32c99d4acd07278145db749f08eb500af7ff7ab055c0be859bfa8e39d3cfd070497931ac7d7053fd4fa1a23335bdf9985e3fde6735fabb16a8fdec880dc2cb767e81b71ebcd9aa2fa782979de0025975f878a5f1a030d38f85477254aacec1c7892df5dfa2c3ef1e8d1f161a9920658a38bccd874413f8c");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            Map<String, List<String>> stringListMap = conn.getHeaderFields();
            for (Map.Entry entry:stringListMap.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue().toString());
            }
            InputStream inputStream = conn.getInputStream();
            //Writer writer = new StringWriter();
            FileUtils.copyInputStreamToFile(inputStream,new File(
                    "E:\\filesystem\\project\\OneForAll\\test\\" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
