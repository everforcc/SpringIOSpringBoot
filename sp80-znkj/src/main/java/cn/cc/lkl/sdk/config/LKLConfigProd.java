package cn.cc.lkl.sdk.config;

import cn.cc.lkl.util.CertUtil;
import com.lkl.laop.sdk.Config2;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;

/**
 * 用的字段
 */
public class LKLConfigProd {

    /**
     * 开放平台鉴权机构
     * 测试环境填1
     * 200028
     */
    public static final String orgCode = "986557";

    public static final String merchantNo = "822491058123JVJ";

    public static final String termNo = "N8006401";

    /**
     * 接入方唯一编号
     * 拉卡拉开放平台进行配置开通
     */
    public static final String appId = "OP10001833";


    /**
     * 商户证书序列号,和商户私钥对应
     * 00dfba8194c41b84cf
     * 1745381c327
     */
    private static final String serialNo = "019a00658e17";

    /**
     * 商户私钥字符串,用于请求签名
     * OP00000003_private_key.pem
     */
    private static final String priKeyLine = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGU4E7AJaYbpoS\n" +
            "NMgnHcTtE6zR1DtaoBbzN58y7oEPPMkNaJ3cW1M4ZhsOl6FnSCD8Dq1UMiV0pdwU\n" +
            "0QTemVHp4yhXIo4A4HQcC+oh+PxesePXB40BnyLUWYXE3oGq35p6OhAmamnD8yFr\n" +
            "CR64PJYR/VsuilBBUvHrbXUuu93lL62thpLb92OVke8EgmwF1DPTbWv3MwPhuNtb\n" +
            "N77CRoxdgxkaMnx/gK1IBgjg6C7wUbR6dBGYZwvMbu/Hnh8HLlVkpH0M02WSVIYl\n" +
            "nZ5fIBzf6RfcT1wBgVTUkGlNjU7dDAsFjHYoL4owdOVJco5GZ9GBrkRCxWVlLu1g\n" +
            "Y0ZEOUnFAgMBAAECggEALZXvOHfVo7t9PAp/lo7Kwu43aypMTlAgGcBZna5FTEeD\n" +
            "r7I+SIcRsgUsz4Hz+5oF36SXfAhcn76d9s6MiYKcwvQdP5zxdii2HsKIXaki9HwI\n" +
            "Z3QfjVpVNp1/ATXF6Zhi03IbAv6AEEjVoX6B12JwR4CkziGQZOriQNRvvUFyZ5GI\n" +
            "pMf5VW59dI1JNFGQskPgV7EvGd172QtLSbqc+Am25PcHfBUl5tGucrWZDJez6BLj\n" +
            "wZcJ+GwS7Gi5DrGzR57qxbzNn8bgbamtmioSh9ewmDRnHpBnUehORxievtZUQUHN\n" +
            "PnPdErQgm8ezQsRAXjDTs2L1myY0XBRo/pRX/nRFbQKBgQD2xHotApXL5nrNcAnA\n" +
            "SDOZeCN3+q1FTq6Q5OP2QqsbayZvR6q5VeZ+oNYDHmYNCXpUw68/hcN7sfA6sx/H\n" +
            "cbHdde0MoZcK+ogQDdy0CnKcFgpj5DjPYh2ekxZcUzuu/0MKMINkNe0TqRv+ypH4\n" +
            "iaOiHiHvv7K0qO0D8mS706tuhwKBgQCLWhJ8RysR1Jr2DXtFopJVsPcdsvcOC/cr\n" +
            "IYhAIPY8Kg+hphfn245P4+dcZTtffurZ6bon15FTn3oqu7eyt/ULq7tNa8VerENB\n" +
            "eyvrGlpHXXF0jED6Pi2TtXSYveaLGFZX+Tj1U0QSIxy8VvE3OK8HzXVGbwY4CQ6B\n" +
            "j6KLSEDsUwKBgDO5YUaX+SAhMnJnXpPgTniHtx+J+tHBOVQDtkjDFqyLnCXNJtrs\n" +
            "R4EZY2KJ96gZ6Xtk/RHajuwZkxoqu6mJile6RTLVmBxkASskTLOsUPOopwrjJolJ\n" +
            "rp2zhQdbJvJ6bOnw20mhCA2L7+NLQKeQ6iysFj7ztpipo+yk3TqFlrR3AoGAT6Y9\n" +
            "GKHJAVkG9YqBIi0o/Se4J1aKlZHLRfDVoun0NDFKW/fGhDTfI9S9AfeutwehDsrk\n" +
            "Jy+0lhp6RoCjH9i7jF8tILRvzIwjqQLQ+IFIhq/nUFl2LlJp1JqLuV4ZrvlLKN9/\n" +
            "aeGc78z3+MKs/wY8LPaHiKme4Vpz2yQRIRE8YpcCgYEA4Kzb1C3wFr+k5pLsF51n\n" +
            "1VjYDncpoOnzt9tJMH9XpEm0qqgQzucLO1RXoz2FPEIauZM85qjn7G+OTO/QnvOp\n" +
            "NrwI6o3Kp6CiD5ey7IUd7LR+FXJqY6P0fO6uyBzBPnTuaU9TZuGcvm7fKZu/eKM9\n" +
            "gYB5hRHyjskPux8bGY3OCYU=\n" +
            "-----END PRIVATE KEY-----\n";

    /**
     * 拉卡拉公钥证书字符串,用于验签
     * lkl-apigw-v2.cer
     */
    private static final String lklCerStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIEMTCCAxmgAwIBAgIGAXUrc4b4MA0GCSqGSIb3DQEBCwUAMHYxCzAJBgNVBAYT\n" +
            "AkNOMRAwDgYDVQQIDAdCZWlKaW5nMRAwDgYDVQQHDAdCZWlKaW5nMRcwFQYDVQQK\n" +
            "DA5MYWthbGEgQ28uLEx0ZDEqMCgGA1UEAwwhTGFrYWxhIE9yZ2FuaXphdGlvbiBW\n" +
            "YWxpZGF0aW9uIENBMB4XDTIwMTAxNTA4NDk1MloXDTMwMTAxMzA4NDk1MlowZTEL\n" +
            "MAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JlaUppbmcxEDAOBgNVBAcMB0JlaUppbmcx\n" +
            "FzAVBgNVBAoMDkxha2FsYSBDby4sTHRkMRkwFwYDVQQDDBBBUElHVy5MQUtBTEEu\n" +
            "Q09NMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwAXZw9lupWcFXouC\n" +
            "Nhm0DQT47Zf4KOIRF8rqT8Ps3pYzT8odROJ8rq4P+lciGrg29czpqrRM22yQktFr\n" +
            "itvcM7JlE6jFbGH3rycnvGvhRYU/j1N9k0ozm8oVwmKX357/OtGzNivBECGSnU9L\n" +
            "Bkp4Nm9M1K4cOwEuZ0xsQEthZjQYF0mDpnlWmVJL5i1Lq834atN2qrb/mzMHBNtD\n" +
            "JnqRV7rPL39lKpe7LJiitsC2JuW1UbWZZU1NNwA/rz2d83C+KD1DLJ0+sMYY2Q3T\n" +
            "OQ4BPAowDEwOH7XAXrHM/0kRm+ZeIFlwevEGIQWmMt1Ogz+AW4Iq0slINc4wOINK\n" +
            "vH9tHwIDAQABo4HVMIHSMIGSBgNVHSMEgYowgYeAFCnH4DkZPR6CZxRn/kIqVsMo\n" +
            "dJHpoWekZTBjMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQmVpSmluZzEQMA4GA1UE\n" +
            "BwwHQmVpSmluZzEXMBUGA1UECgwOTGFrYWxhIENvLixMdGQxFzAVBgNVBAMMDkxh\n" +
            "a2FsYSBSb290IENBggYBaiUALIowHQYDVR0OBBYEFIya0Yc4OSBer55JLyA0AYe9\n" +
            "m8mTMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMA0GCSqGSIb3DQEBCwUA\n" +
            "A4IBAQCBEwOlk3mXigNv94Drn3dcaY2ml/y+8yNpAIuUhuBE00WFoqEX5lOatFy5\n" +
            "fzdXuC12lBVQ8SjSm3aH7k2X0eXqDzkOHiur2ZBRKmJ++J4TeenuSUOjSIbQK/DT\n" +
            "vxaqFUjYwFSVCyizpy7wfU4wKt+jOuFb9LyULJ9lkM1dV9Kh7Lmd9+nlJYYuPEPU\n" +
            "LJkkVZqSALSiiJudXnTwlISjZTXEAkJpdIlMw+hvPTAkoG95B95M+OV/uLbItGK+\n" +
            "qT4+RHWo8EbBDPQYo6J4QYHOxRlfMoGBMyrz6XDt7ELLmT7ld4aE02w6KQPfK3gq\n" +
            "kLDT+/STozvaNmXzBJh7J6KqxJBH\n" +
            "-----END CERTIFICATE-----";
    private static final String lklCerLineStr = "";

    /**
     * 拉卡拉开放平台服务地址
     * 测试 https://test.wsmsd.cn/sit
     * 生产 https://s2.lakala.com
     */
    public static final String serverUrl = "https://s2.lakala.com";


    public static final String sm4Key = "";

    /**
     * lkl内部商户号
     */
    public static final String merInnerNo = "4002025101124353942";

    /**
     * todo 支付宝微信的子商户号不同，这个接口待定
     */
    public static final String subMchId = "817367616";

    public static boolean initSDKProd() throws SDKException {
        //方式4：
        Config2 config = new Config2();
        config.setAppId(appId);
        config.setSerialNo(serialNo);
        config.setPriKey(priKeyLine);
        config.setLklCer(lklCerStr);
        config.setLklNotifyCer(lklCerStr);
//        config.setPriKey(priKeyStr);
//        config.setLklCer(lklNotifyCerStr);
//        config.setLklNotifyCer(lklNotifyCerStr);
        config.setServerUrl(serverUrl);
        config.setSm4Key(sm4Key);
        return LKLSDK.init(config);
    }

}
