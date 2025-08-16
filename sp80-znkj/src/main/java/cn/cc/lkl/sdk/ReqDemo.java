package cn.cc.lkl.sdk;

import cn.cc.lkl.dto.CommonRequestDTO;
//import cn.cc.lkl.dto.V3TkbsCustomerFileUpload;
import cn.cc.lkl.util.CertUtil;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.Config;
import com.lkl.laop.sdk.Config2;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V2LaepCreateEleReceiptRequest;
import com.lkl.laop.sdk.request.V3LabsTransMicropayRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
public class ReqDemo {

    public static final String ENCODING = "utf-8";

    /**
     * 接入方唯一编号
     * 拉卡拉开放平台进行配置开通
     */
    private static final String appId = "OP00000003";


    /**
     * 商户证书序列号,和商户私钥对应
     */
    private static final String serialNo = "00dfba8194c41b84cf";


    /**
     * 商户私钥文件路径
     * 商户私钥地址,用于请求签名
     */
    private static final String priKeyPath = "D:/cache/BaiduSyncdisk/znkj/project/拉卡拉/文档/sdk/V3商户测试私钥、公钥证书/OP00000003_private_key.pem";


    /**
     * 拉卡拉公钥证书地址,用于验签
     */
    private static final String lklCerPath = "D:/cache/BaiduSyncdisk/znkj/project/拉卡拉/文档/sdk/V3商户测试私钥、公钥证书/OP00000003_cert.cer";


    /**
     * 拉卡拉支付平台证书地址2(用于拉卡拉通知验签，当前同lklCerPath)
     */
    private static final String lklNotifyCerPath = lklCerPath;

    /**
     * 拉卡拉开放平台服务地址
     * 测试 https://test.wsmsd.cn/sit
     * 生产 https://s2.lakala.com
     */
    private static final String serverUrl = "https://test.wsmsd.cn/sit";


    private static final String sm4Key = "如果需要密文传输请申请拉卡拉SM4密钥";

    /**
     * 商户私钥字符串,用于请求签名
     */
    private static final String priKeyStr = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDvDBZyHUDndAGx\n" +
            "rIcsCV2njhNO3vCEZotTaWYSYwtDvkcAb1EjsBFabXZaKigpqFXk5XXNI3NIHP9M\n" +
            "8XKzIgGvc65NpLAfRjVql8JiTvLyYd1gIUcOXMInabu+oX7dQSI1mS8XzqaoVRhD\n" +
            "ZQWhXcJW9bxMulgnzvk0Ggw07AjGF7si+hP/Va8SJmN7EJwfQq6TpSxR+WdIHpbW\n" +
            "dhZ+NHwitnQwAJTLBFvfk28INM39G7XOsXdVLfsooFdglVTOHpNuRiQAj9gShCCN\n" +
            "rpGsNQxDiJIxE43qRsNsRwigyo6DPJk/klgDJa417E2wgP8VrwiXparO4FMzOGK1\n" +
            "5quuoD7DAgMBAAECggEBANhmWOt1EAx3OBFf3f4/fEjylQgRSiqRqg8Ymw6KGuh4\n" +
            "mE4Md6eW/B6geUOmZjVP7nIIR1wte28M0REWgn8nid8LGf+v1sB5DmIwgAf+8G/7\n" +
            "qCwd8/VMg3aqgQtRp0ckb5OV2Mv0h2pbnltkWHR8LDIMwymyh5uCApbn/aTrCAZK\n" +
            "NXcPOyAn9tM8Bu3FHk3Pf24Er3SN+bnGxgpzDrFjsDSHjDFT9UMIc2WdA3tuMv9X\n" +
            "3DDn0bRCsHnsIw3WrwY6HQ8mumdbURk+2Ey3eRFfMYxyS96kOgBC2hqZOlDwVPAK\n" +
            "TPtS4hoq+cQ0sRaJQ4T0UALJrBVHa+EESgRaTvrXqAECgYEA+WKmy9hcvp6IWZlk\n" +
            "9Q1JZ+dgIVxrO65zylK2FnD1/vcTx2JMn73WKtQb6vdvTuk+Ruv9hY9PEsf7S8gH\n" +
            "STTmzHOUgo5x0F8yCxXFnfji2juoUnDdpkjtQK5KySDcpQb5kcCJWEVi9v+zObM0\n" +
            "Zr1Nu5/NreE8EqUl3+7MtHOu1TMCgYEA9WM9P6m4frHPW7h4gs/GISA9LuOdtjLv\n" +
            "AtgCK4cW2mhtGNAMttD8zOBQrRuafcbFAyU9de6nhGwetOhkW9YSV+xRNa7HWTeI\n" +
            "RgXJuJBrluq5e1QGTIwZU/GujpNaR4Qiu0B8TodM/FME7htsyxjmCwEfT6SDYlke\n" +
            "MzTbMa9Q0DECgYBqsR/2+dvD2YMwAgZFKKgNAdoIq8dcwyfamUQ5mZ5EtGQL2yw4\n" +
            "8zibHh/LiIxgUD1Kjk/qQgNsX45NP4iOc0mCkrgomtRqdy+rumbPTNmQ0BEVJCBP\n" +
            "scd+8pIgNiTvnWpMRvj7gMP0NDTzLI3wnnCRIq8WAtR2jZ0Ejt+ZHBziLQKBgQDi\n" +
            "bEe/zqNmhDuJrpXEXmO7fTv3YB/OVwEj5p1Z/LSho2nHU3Hn3r7lbLYEhUvwctCn\n" +
            "Ll2fzC7Wic1rsGOqOcWDS5NDrZpUQGGF+yE/JEOiZcPwgH+vcjaMtp0TAfRzuQEz\n" +
            "NzV8YGwxB4mtC7E/ViIuVULHAk4ZGZI8PbFkDxjKgQKBgG8jEuLTI1tsP3kyaF3j\n" +
            "Aylnw7SkBc4gfe9knsYlw44YlrDSKr8AOp/zSgwvMYvqT+fygaJ3yf9uIBdrIilq\n" +
            "CHKXccZ9uA/bT5JfIi6jbg3EoE9YhB0+1aGAS1O2dBvUiD8tJ+BjAT4OB0UDpmM6\n" +
            "QsFLQgFyXgvDnzr/o+hQJelW\n" +
            "-----END PRIVATE KEY-----\n";
    private static final String priKeyLineStr = "-----BEGIN PRIVATE KEY----- MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDvDBZyHUDndAGxrIcsCV2njhNO3vCEZotTaWYSYwtDvkcAb1EjsBFabXZaKigpqFXk5XXNI3NIHP9M8XKzIgGvc65NpLAfRjVql8JiTvLyYd1gIUcOXMInabu+oX7dQSI1mS8XzqaoVRhDZQWhXcJW9bxMulgnzvk0Ggw07AjGF7si+hP/Va8SJmN7EJwfQq6TpSxR+WdIHpbWdhZ+NHwitnQwAJTLBFvfk28INM39G7XOsXdVLfsooFdglVTOHpNuRiQAj9gShCCNrpGsNQxDiJIxE43qRsNsRwigyo6DPJk/klgDJa417E2wgP8VrwiXparO4FMzOGK15quuoD7DAgMBAAECggEBANhmWOt1EAx3OBFf3f4/fEjylQgRSiqRqg8Ymw6KGuh4mE4Md6eW/B6geUOmZjVP7nIIR1wte28M0REWgn8nid8LGf+v1sB5DmIwgAf+8G/7qCwd8/VMg3aqgQtRp0ckb5OV2Mv0h2pbnltkWHR8LDIMwymyh5uCApbn/aTrCAZKNXcPOyAn9tM8Bu3FHk3Pf24Er3SN+bnGxgpzDrFjsDSHjDFT9UMIc2WdA3tuMv9X3DDn0bRCsHnsIw3WrwY6HQ8mumdbURk+2Ey3eRFfMYxyS96kOgBC2hqZOlDwVPAKTPtS4hoq+cQ0sRaJQ4T0UALJrBVHa+EESgRaTvrXqAECgYEA+WKmy9hcvp6IWZlk9Q1JZ+dgIVxrO65zylK2FnD1/vcTx2JMn73WKtQb6vdvTuk+Ruv9hY9PEsf7S8gHSTTmzHOUgo5x0F8yCxXFnfji2juoUnDdpkjtQK5KySDcpQb5kcCJWEVi9v+zObM0Zr1Nu5/NreE8EqUl3+7MtHOu1TMCgYEA9WM9P6m4frHPW7h4gs/GISA9LuOdtjLvAtgCK4cW2mhtGNAMttD8zOBQrRuafcbFAyU9de6nhGwetOhkW9YSV+xRNa7HWTeIRgXJuJBrluq5e1QGTIwZU/GujpNaR4Qiu0B8TodM/FME7htsyxjmCwEfT6SDYlkeMzTbMa9Q0DECgYBqsR/2+dvD2YMwAgZFKKgNAdoIq8dcwyfamUQ5mZ5EtGQL2yw48zibHh/LiIxgUD1Kjk/qQgNsX45NP4iOc0mCkrgomtRqdy+rumbPTNmQ0BEVJCBPscd+8pIgNiTvnWpMRvj7gMP0NDTzLI3wnnCRIq8WAtR2jZ0Ejt+ZHBziLQKBgQDibEe/zqNmhDuJrpXEXmO7fTv3YB/OVwEj5p1Z/LSho2nHU3Hn3r7lbLYEhUvwctCnLl2fzC7Wic1rsGOqOcWDS5NDrZpUQGGF+yE/JEOiZcPwgH+vcjaMtp0TAfRzuQEzNzV8YGwxB4mtC7E/ViIuVULHAk4ZGZI8PbFkDxjKgQKBgG8jEuLTI1tsP3kyaF3jAylnw7SkBc4gfe9knsYlw44YlrDSKr8AOp/zSgwvMYvqT+fygaJ3yf9uIBdrIilqCHKXccZ9uA/bT5JfIi6jbg3EoE9YhB0+1aGAS1O2dBvUiD8tJ+BjAT4OB0UDpmM6QsFLQgFyXgvDnzr/o+hQJelW -----END PRIVATE KEY-----";


    /**
     * 拉卡拉公钥证书字符串,用于验签
     */
    private static final String lklCerStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDYTCCAkmgAwIBAgIJAN+6gZTEG4TPMA0GCSqGSIb3DQEBCwUAMEkxCzAJBgNV\n" +
            "BAYTAlVTMREwDwYDVQQIEwhzaGFuZ2hhaTERMA8GA1UEBxMIc2hhbmdoYWkxFDAS\n" +
            "BgNVBAMUC2xha2FsYV8yMDIxMB4XDTIxMDYxODA3MjEzNFoXDTMxMDYxOTA3MjEz\n" +
            "NFowSTELMAkGA1UEBhMCVVMxETAPBgNVBAgTCHNoYW5naGFpMREwDwYDVQQHEwhz\n" +
            "aGFuZ2hhaTEUMBIGA1UEAxQLbGFrYWxhXzIwMjEwggEiMA0GCSqGSIb3DQEBAQUA\n" +
            "A4IBDwAwggEKAoIBAQDvDBZyHUDndAGxrIcsCV2njhNO3vCEZotTaWYSYwtDvkcA\n" +
            "b1EjsBFabXZaKigpqFXk5XXNI3NIHP9M8XKzIgGvc65NpLAfRjVql8JiTvLyYd1g\n" +
            "IUcOXMInabu+oX7dQSI1mS8XzqaoVRhDZQWhXcJW9bxMulgnzvk0Ggw07AjGF7si\n" +
            "+hP/Va8SJmN7EJwfQq6TpSxR+WdIHpbWdhZ+NHwitnQwAJTLBFvfk28INM39G7XO\n" +
            "sXdVLfsooFdglVTOHpNuRiQAj9gShCCNrpGsNQxDiJIxE43qRsNsRwigyo6DPJk/\n" +
            "klgDJa417E2wgP8VrwiXparO4FMzOGK15quuoD7DAgMBAAGjTDBKMAkGA1UdEwQC\n" +
            "MAAwEQYJYIZIAYb4QgEBBAQDAgTwMAsGA1UdDwQEAwIFoDAdBgNVHSUEFjAUBggr\n" +
            "BgEFBQcDAgYIKwYBBQUHAwEwDQYJKoZIhvcNAQELBQADggEBAI21YYAlH+Pc1ISv\n" +
            "nbQrGqL8suGL0Hh/8hGaFfrJEJEKr9OeC8jElUhck2MTmfu/Y1lB7r8RBrhGPXi4\n" +
            "kTXmB6ADs/9+ezNW3WXyFj7fhs3JcZ3mo33T9wyQySDKd//JrEtrTsc/s2PZ602y\n" +
            "qNmPomXSzjrlugaMyC7LI9sR44mc7sQnchjHoxrQFD5/usTFW72UQfYCORsQWYMt\n" +
            "0KKEyAcpRL51RE3xbX1WDtduFYGP62PbwLAn2nCL/j1wlF5hltWj7sditWqKgso5\n" +
            "F8BTffn2Bb0RdsNxqwMy1cTPrWLeXVOqMDu3ge7hvoav8lZKTjk5Kmqhs7wNAQXK\n" +
            "mg9qSwo=\n" +
            "-----END CERTIFICATE-----\n";
    private static final String lklCerLineStr = "-----BEGIN CERTIFICATE----- MIIDYTCCAkmgAwIBAgIJAN+6gZTEG4TPMA0GCSqGSIb3DQEBCwUAMEkxCzAJBgNVBAYTAlVTMREwDwYDVQQIEwhzaGFuZ2hhaTERMA8GA1UEBxMIc2hhbmdoYWkxFDASBgNVBAMUC2xha2FsYV8yMDIxMB4XDTIxMDYxODA3MjEzNFoXDTMxMDYxOTA3MjEzNFowSTELMAkGA1UEBhMCVVMxETAPBgNVBAgTCHNoYW5naGFpMREwDwYDVQQHEwhzaGFuZ2hhaTEUMBIGA1UEAxQLbGFrYWxhXzIwMjEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDvDBZyHUDndAGxrIcsCV2njhNO3vCEZotTaWYSYwtDvkcAb1EjsBFabXZaKigpqFXk5XXNI3NIHP9M8XKzIgGvc65NpLAfRjVql8JiTvLyYd1gIUcOXMInabu+oX7dQSI1mS8XzqaoVRhDZQWhXcJW9bxMulgnzvk0Ggw07AjGF7si+hP/Va8SJmN7EJwfQq6TpSxR+WdIHpbWdhZ+NHwitnQwAJTLBFvfk28INM39G7XOsXdVLfsooFdglVTOHpNuRiQAj9gShCCNrpGsNQxDiJIxE43qRsNsRwigyo6DPJk/klgDJa417E2wgP8VrwiXparO4FMzOGK15quuoD7DAgMBAAGjTDBKMAkGA1UdEwQCMAAwEQYJYIZIAYb4QgEBBAQDAgTwMAsGA1UdDwQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwDQYJKoZIhvcNAQELBQADggEBAI21YYAlH+Pc1ISvnbQrGqL8suGL0Hh/8hGaFfrJEJEKr9OeC8jElUhck2MTmfu/Y1lB7r8RBrhGPXi4kTXmB6ADs/9+ezNW3WXyFj7fhs3JcZ3mo33T9wyQySDKd//JrEtrTsc/s2PZ602yqNmPomXSzjrlugaMyC7LI9sR44mc7sQnchjHoxrQFD5/usTFW72UQfYCORsQWYMt0KKEyAcpRL51RE3xbX1WDtduFYGP62PbwLAn2nCL/j1wlF5hltWj7sditWqKgso5F8BTffn2Bb0RdsNxqwMy1cTPrWLeXVOqMDu3ge7hvoav8lZKTjk5Kmqhs7wNAQXKmg9qSwo= -----END CERTIFICATE-----";


    /**
     * 拉卡拉支付平台证书字符串2(用于拉卡拉通知验签，当前同lklCerStr)
     */
    private static final String lklNotifyCerStr = lklCerStr;

    public static void main(String[] args) {
        //初始化配置(全局只需配置一次)
        try {
            boolean result = demoConfig1();
            System.out.println("初始化配置结果：" + result);
//            demoReq();
            demoReq2();
//            demoUpload();
        } catch (SDKException e) {
//            e.printStackTrace();
            System.err.println("SDKException: " + e.getMessage());
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * 公钥私钥地址文件
     * @throws SDKException
     */
    public static boolean demoConfig1() throws SDKException {
        //方式1：
        return  LKLSDK.init(new Config(appId, serialNo, priKeyPath, lklCerPath, lklNotifyCerPath, serverUrl));
    }

    /**
     *
     * @throws SDKException
     */
    public static void demoConfig2() throws SDKException {
        //方式2：
        LKLSDK.init(new Config(appId, serialNo, priKeyPath, lklCerPath, lklNotifyCerPath, sm4Key, serverUrl));
    }

    public static boolean demoConfig3() throws SDKException {
        //方式3：
        Config config = new Config();
        config.setAppId(appId);
        config.setSerialNo(serialNo);
        config.setPriKeyPath(priKeyPath);
        config.setLklCerPath(lklCerPath);
        config.setLklNotifyCerPath(lklNotifyCerPath);
        config.setServerUrl(serverUrl);
        config.setSm4Key(sm4Key);
        return LKLSDK.init(config);
    }

    /**
     *
     */
    public static boolean demoConfig4() throws SDKException {
        //方式4：
        Config2 config = new Config2();
        config.setAppId(appId);
        config.setSerialNo(serialNo);
        config.setPriKey(CertUtil.convertToStandardFormat(priKeyLineStr));
        config.setLklCer(CertUtil.convertToStandardFormat(lklCerLineStr));
        config.setLklNotifyCer(CertUtil.convertToStandardFormat(lklCerLineStr));
//        config.setPriKey(priKeyStr);
//        config.setLklCer(lklCerStr);
//        config.setLklNotifyCer(lklCerStr);
        config.setServerUrl(serverUrl);
//        config.setSm4Key(sm4Key);
        return LKLSDK.init(config);
    }

    public static void demoReq1() throws SDKException {

    }

//    public static void demoReq2() throws SDKException {
//
//    }

    public static void demoReq3() throws SDKException {

    }

    public static void demoReq() throws SDKException {

        //根据请求接口拼装参数
//        V3LabsTradeQueryRequest  v3LabsQueryTradequeryRequest=new  V3LabsTradeQueryRequest();
//        v3LabsQueryTradequeryRequest.setMerchantNo("xxxx");
//        v3LabsQueryTradequeryRequest.setTermNo("xxx");
//        v3LabsQueryTradequeryRequest.setOutTradeNo("xxxx");

        V2LaepCreateEleReceiptRequest v3LabsQueryTradequeryRequest = new V2LaepCreateEleReceiptRequest();
//        commRequest.setReqData(v3LabsQueryTradequeryRequest);
        v3LabsQueryTradequeryRequest.setLklAppId(appId);
        v3LabsQueryTradequeryRequest.setMercId("1951582");
        v3LabsQueryTradequeryRequest.setAcctSysSq("aaaa");
        //发起请求-正常请求
        String response = LKLSDK.httpPost(v3LabsQueryTradequeryRequest, false, false);

        //处理响应
        log.info(response);

        //发起请求2(密文传输接口)-请求报文加密,响应信息无需解密
//        String response2 = LKLSDK.httpPost(v3LabsQueryTradequeryRequest, true, false);
//
//
//        //发起请求3(密文传输接口)-请求报文加密,响应信息需解密
//        String response3 = LKLSDK.httpPost(v3LabsQueryTradequeryRequest, true, true);
//
//
//        //发起请求4-自定义接口参数实体,直接发送接口地址
//        String response4 = LKLSDK.httpPost("url", "bodyJson");
    }
    public static void demoReq2() throws SDKException {

        V3LabsTransMicropayRequest commRequest = new V3LabsTransMicropayRequest();
        String body = "{\n" +
                "    \"req_time\": \"20210907150256\",\n" +
                "    \"version\": \"3.0\",\n" +
                "    \"out_org_code\": \"OP00000003\",\n" +
                "    \"req_data\": {\n" +
                "        \"merchant_no\": \"822290070111135\",\n" +
                "        \"term_no\": \"29034705\",\n" +
                "        \"out_trade_no\": \"FD660E1FAA3A4470933CDEDAE1EC1D8E\",\n" +
                "        \"auth_code\": \"135178236713755038\",\n" +
                "        \"total_amount\": \"123\",\n" +
                "        \"location_info\": {\n" +
                "            \"request_ip\": \"10.176.1.192\",\n" +
                "            \"location\": \"+37.123456789,-121.123456789\"\n" +
                "        },\n" +
                "        \"out_order_no\": \"08F4542EEC6A4497BC419161747A92FA\"\n" +
                "    }\n" +
                "}";
//        commRequest = JSONObject.parseObject(body, V3LabsTransMicropayRequest.class);
        commRequest.setMerchantNo("00dfba8194c41b84cf");
        commRequest.setTermNo("29034705");
        commRequest.setOutTradeNo("FD660E1FAA3A4470933CDEDAE1EC1D8E");
        commRequest.setAuthCode("135178236713755038");
        commRequest.setTotalAmount("123");
//        JSONObject locationInfo = new JSONObject();
//        locationInfo.put("request_ip", "10.176.1.192");
//        locationInfo.put("location", "+37.123456789,-121.123456789");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.192");
        locationInfo.setLocation("+37.123456789,-121.123456789");
        commRequest.setLocationInfo(locationInfo);
//        commRequest.out
        commRequest.setPayOrderNo("08F4542EEC6A4497BC419161747A92FA");
        System.out.println("commRequest.toBody():" + commRequest.toBody());
        try {
            String response = LKLSDK.httpPost(commRequest);
            System.out.println("response:" + response);
        }catch (SDKException e){
            e.printStackTrace();
            String err = e.getMessage();
            log.error(err);
//            e.
            String errInfo = err;
            if(err.contains("code")){
                // 从{截取到最后一位
                errInfo = err.substring(err.indexOf("{"), err.length() - 2);
                System.out.println("errInfo:" + errInfo);
                errInfo = err.substring(err.indexOf("{"), err.length() - 1);
                System.out.println("errInfo:" + errInfo);
                errInfo = err.substring(err.indexOf("{"), err.length());
                System.out.println("errInfo:" + errInfo);
                log.info("处理后的错误信息为: " + errInfo);
                JSONObject jsonObject = JSONObject.parseObject(errInfo);
                System.out.println("jsonObject:" + jsonObject);
                System.out.println("jsonObject.getString(\"code\"): " + jsonObject.getString("code"));
                errInfo = jsonObject.getString("msg");
                System.out.println("jsonObject.getString(\"msg\"): " + jsonObject.getString("msg"));
            }
            throw new RuntimeException("处理后的错误信息为: " + errInfo);
        }catch (Exception e){
            System.out.println("e.getMessage(): " + e.getMessage());
            e.getCause().printStackTrace();
            System.out.println(e.getCause().getMessage());
        }
    }


    public static void demoUpload() throws SDKException, IOException {
//        V3TkbsCustomerFileUpload commRequest = new V3TkbsCustomerFileUpload();
//        String response2 = LKLSDK.httpPost(commRequest);
        CommonRequestDTO commRequest = new CommonRequestDTO();
        JSONObject reqData = new JSONObject();

        reqData.put("is_ocr","false");
        reqData.put("sourcechnl","0");
        reqData.put("img_type","CHECKSTAND_IMG");
//        File file = new File("");
        // file to base64

        String fileBase64 = Base64.encodeBase64String(Files.readAllBytes(Paths.get("D:/cache/BaiduSyncdisk/znkj/project/汇付/用户信息/slk/mt.jpg")));
        System.out.println("fileBase64:" + fileBase64);
        reqData.put("file_base64",fileBase64);
        commRequest.setReqData(reqData);

        String response4 = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/customer/file/upload", JSONObject.toJSONString(commRequest));

    }

    public final String getAuthorization(String body) throws IOException {
        String nonceStr = generateNonceStr();
        long timestamp = generateTimestamp();

        String message = appId + "\n" + serialNo + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";

        System.out.println("getToken message :  " + message);

        PrivateKey merchantPrivateKey = loadPrivateKey(new FileInputStream(new File(priKeyPath)));

        String signature = this.sign(message.getBytes(ENCODING), merchantPrivateKey);

        String authorization = "appid=\"" + appId + "\"," + "serial_no=\"" + serialNo + "\"," + "timestamp=\""
                + timestamp + "\"," + "nonce_str=\"" + nonceStr + "\"," + "signature=\"" + signature + "\"";
        System.out.println("authorization message :" + authorization);

        return authorization;
    }

    public String sign(byte[] message, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(message);
            return new String(Base64.encodeBase64(sign.sign()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        }
    }

    protected long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    protected String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
    public static PrivateKey loadPrivateKey(InputStream inputStream) {
        try {
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                array.write(buffer, 0, length);
            }

            String privateKey = array.toString("utf-8").replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        } catch (IOException e) {
            throw new RuntimeException("无效的密钥");
        }
    }

}
