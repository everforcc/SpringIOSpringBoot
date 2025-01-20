package cn.cc.huifu.config;


import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.config.MerConfig;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class PayConfig {
    private static final String DEMO_PRODUCT_ID = "PAYUN";
    private static final String DEMO_SYS_ID = "6666000151772004";
    private static final String DEMO_RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCme/6XTeOuMKtLmzmGVVkG2DMMaHS0W7n3JS8Fq83TdSMEDXihxxqdBImKfg21YiPB7HyZ16f0Kf3VBxPcRuSLx3+X2f9M6OxZZ9zNLg6ogUJKd/zq0Ai2hMQsRv0yYzbMghTfPt7SVh8YBt2ny6O8vHd8X/inYtWl/z1jmgwkeSyXfnc7WLrSXID1hPk5XonIbojsHeFJb4k3xFGz65rWaZU/F14kE7J0Cc9XvY1mZGpCLJ1gnwHEjEyFi9UFEi5UZ13fMJEpEOiJmSuB4uyS5rXKJATD+nSrODxbPBAw91FRfTTBas73zb084atnzlPkSZaN6JLvIXHgnFewZp4FAgMBAAECggEAQaGoICXImCb/oHK1HswOqLAaj82uFFwE1/WEsAumvLH09+gHol9HkpbzUfGXneXuisib5loqEAtt0clUfwZDmwcuucJq7vr0EdrsJMb/aFZst8bNohQZGzsDyyDBOFf8hmrRp6C1Xt4sq+ApCbkx93mk8Rc0g2hnKWWR4e4c01N+2GmSzYkQV4Je0v9sHToHzb6P1cewmvKfDwoh/W5VS/WuS9hXo3MjP/LPGjCGG0tHdyzzqkYfri1dDmN92EgxKgRf21IBF7bSbe0zFRywk/dpKaoQ9bKRkw/gdy99hU5R68/CBSDpx9D2lBFN6GZr/Rm91Z3CilzHHF2Bn5KuAQKBgQDYbz1GwX4RqrNax6H8B2zfeL7u5nQj1p215Oam47E+9GrKZTALaP5cBtyhr/FI+sDahi+rZpVCVBEqjC7AX5kql0ttdUxi3MuNT/ePNexwBkgRN1+aFdxEbkdfuHHUkyZGB2c/YkWR211LnJfgsHPqWOTtq3xPabmTdAcCAq2z5QKBgQDE6yxtuGYdyz7dmD5lMJ1kUZrHH3hKwmniNbj+uWUtShX+9KF9+WauRA4TFyKgz4EhmuOXLnT6zx48nGo8rflWULubwdJ1yCFEgc9Cdjcxb2i6hS4iyQjVJDvvO9v7cKygprH7WQM+NrCWrJ2jnp4vAkeoYCXfAarB6mCj/GTfoQKBgAMHQ287A92RauMJSg3xuKdD1EAZf2SE+z3xTvzk0LUVzGy6j7qJJI37Fpk4kgK4Z3xnRkF6tPnxnS/gOEM+0zDSXx7P9lqnxj1a8hbR5VYH8Hg1MyQ/zA41ENCZDxavXGi8b8hmQpzw2dRWA5YEQgr0ZXeh36Jo00zp4bM4gF+1AoGAFuSMRrkwTfhp95MSdesf8SC3kLys3GwbqSu7tQlSpOA/DGPePmn3MP1CcZ+36+AT0BdUrCe/e8UGa993TlSfbxR1qxbuY7j/fRpmQQDDS+D62OxQv8SFXVoYCaj42xNvTkHy4RipEtuCzhGFRhdTpn1qXT3ISVPFSUP5NjdKduECgYAOcKl18FDXz73BcBLE9YtIgBruUs7o5hwwu8Lhi6cRlhrcaC0qx/8RiNk8gm86lLKXJahlwhd7GOT0paPDF0k3OXb5Q/SQj3smQYGNNrl/pZFJEuoBvCx6+QJuNUnKLQuyyN30XCLRV4b5u5TjsHiA1F0ik33MS9UB0xS3svozcw==";
    private static final String DEMO_RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAurJUgBL1JF822XxaBh9srrx7hC9oJb/ACrYvvqi6K3OPRmGiZPR640laiKQOQC5jf9e1V0hpXiB1lm8xiNzpFqLDcQzTgQBDTI6mWLpMQVDrAvySH3rnfEV7Ckfc3Mh/t2gfzVjHczalVm706jpAxiBz8amAsffBi6oL6LrhKlwoa2N28+PAWS3cKT0LihQiLBK2Oly4iwngV9YfOiEbW4lGYI1O+YInCbYkz+tS9C368Vx0jlr5rpspn0IHQRFmrbcSE59YEzh+4hzoobJ2pFyyT9IUiVEwCSaDzBKf4HjddaLM2e5SlmXrEGwWc9qEDZK5pXEyw+X/26EbjfNpiwIDAQAB";
    private  static final String DEMO_HUIFU_ID = "6666000151824676";

    private static final String SYS_ID= "6666000151772004";


    private static  MerConfig merConfig = null;

    public static String getZnHuifuId(){
        return DEMO_HUIFU_ID;
    }

    public static String getSysId(){
        return SYS_ID;
    }


    public static enum TradeType{
        T_JSAPI("微信公众号"),
        T_MINIAPP("微信小程序"),
        A_JSAPI("支付宝JS"),
        A_NATIVE("支付宝正扫"),
        U_NATIVE("银联正扫"),
        U_JSAPI("银联JS"),
        D_NATIVE("数字人民币正扫"),
        T_H5("微信直连H5支付"),
        T_APP("微信APP支付"),
        T_NATIVE("微信正扫"),
        T_MICROPAY("微信反扫"),
        A_MICROPAY("支付宝反扫"),
        U_MICROPAY("银联反扫"),
        D_MICROPAY("数字人民币反扫"),
        ;

        private String typeName;

        TradeType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }
    public static MerConfig getMerchantConfig() {
        if (merConfig == null){
            /**
             * 单商户模式
             */
            merConfig = new MerConfig();
            merConfig.setProcutId(DEMO_PRODUCT_ID);
            merConfig.setSysId(DEMO_SYS_ID);
            merConfig.setRsaPrivateKey(DEMO_RSA_PRIVATE_KEY);
            merConfig.setRsaPublicKey(DEMO_RSA_PUBLIC_KEY);

        }

        return merConfig;
    }

    static {
        try {
            BasePay.debug = true;
            BasePay.initWithMerConfig(getMerchantConfig());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String decoder(String encryptContent)  {
        try{
            byte[] inputByte = Base64.getDecoder().decode(encryptContent.getBytes("UTF-8"));
            byte[] decoded = Base64.getDecoder().decode(PayConfig.getMerchantConfig().getRsaPrivateKey());
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String decrypt = new String(cipher.doFinal(inputByte));
            return decrypt;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
