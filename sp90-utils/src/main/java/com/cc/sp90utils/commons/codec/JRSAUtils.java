/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-22 11:20
 * Copyright
 */

package com.cc.sp90utils.commons.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JRSAUtils {

    private static final String ALGORITHM = "RSA";

    /* 生成测试用的 privateKey */
    public static final String PRIVATEKEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCLImLjDH9IzO112UawWpOv++T/qDBBCZcJ9tBmaB3EKjRNO99DVix+iMOL3S0yNqtbgZjVVpavlGBJvCAHAnzdFOqVKbP8L+nvIGHe6VzSkgNZqw7S3RyuFfvzZIQM7XweUu+dqN70bTa1w2fMmUQ69SvpTFseJBOY86MylGzka7DFJEHk8u8gLj0hOQo0StA8y8MdtjTpFHMEWTxjIFS+/IWv1zFPwrivXvtyGwIONxQdGvVNi8+SU7jgS5RtET5KJR+raa5qAkx2PmKhbgfPJfHGyaARzHHYvqn9KqNgpEYwetKlmO10yP57oay9WEb7ngTRNeGWdmSW0uT63m5XAgMBAAECggEAMSDKNAvEogsiSfuXl0vUXE6glJlpuH1PZMhO2+oloI+aei8K7bbu7RsTmT5W6CUfqqb/NU1m1caZnU+dK3x4ZIQvNM5N7F1qx/HyPQi/qua94hqhmFlU+C9xEIdMjVgJ/JciXtQzLbGmoNXHwBnaWssZXz6D6gepF9fnp0N7k8fhjo1UJMM+Lofga347Qu5BcW+olRt72bAEmO1s1m+F7rTl7QsQPVVHeH2g7eES5OZf9NOHV60PednrjOvNqwmitvpNgehkxqg4XOOEGwl16UEDg19DBtFGGm2iZRixEaPT06lgxJkFJjpVUfOvIEO5St3tDFl9/zRRFxcfSFE+SQKBgQC/fQfdEpz18vpkfDWT6zBVg+8vh0FwTnAeTossBVLKC7b/AzUHq8i2uPmHmMJJGXziMfxq+GL6YccCqa0W4LMgCTEyqPak/Wp5H89Xm1o8Szp9ADB6ZTAf+Rv150kWBGrAGDWWxDVYgz0xd4utiIfvuQcA4P1gZMVf+0YcTO/ZBQKBgQC6AhBORmaI4eji82sLRmup7gFcCKp0kGvojTRzd4T9NPXj7s9lAltqijmVqWxcc16Cdo7mx9otBx2jf0ZCcUhzAjpEgzVPmAONli2Kl3pj5/AMukQSPIKC+Qb0cI90Zjn44tudRqXAB5mAWU/xKEgTko8jxgWc4EoOEp1RghAYqwKBgQCC2r0x7JNIW2eSnXK7h1+7QHKGBybHyUJC46elSGn/2iuObrGhcVmyzArxB4nVDE7UABafPufcdE7b2fmHhbvCq6VsGhyaOPa2mO9GzcnHJB1u6F84dMeDQedCjm/aVOBiLukIA0L1HpIFqMzRpFhIkCdiCV38rkBWy85obocExQKBgQCp526rgSTxjaEjhQNXJjj73FpDjerdVHoszqaFfjvs6e5dCGtKsa99DstwyHQhsoQ2mUMIzK6eiKxrBrvPvxhVbUh6Anu0u38rWm049gqmrVqY1Z+OJNV1+zB+IT74LNRxYg8Gh1ypnod5NJuQBaJotmunWp8P39PlLN2kcTBwxwKBgGcMoaOPPakjQIVaoA56/tNt125+CjhWhS0eJ1owqyD2tsRsdvEHkVjN4AesE6OU9VrilCFcg84BWSnkJ2dD+doFk4C6Gz8+20VqV5vjCz6fRMdP6LNLB2XSkjm3cbb2YAwEf0CtGtWNKMTl1EKJ+CmcgvqsxwKjLl3v0YW3/Qxy";
    /* 生成测试用的 publicKey */
    public static final String PUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiyJi4wx/SMztddlGsFqTr/vk/6gwQQmXCfbQZmgdxCo0TTvfQ1YsfojDi90tMjarW4GY1VaWr5RgSbwgBwJ83RTqlSmz/C/p7yBh3ulc0pIDWasO0t0crhX782SEDO18HlLvnaje9G02tcNnzJlEOvUr6UxbHiQTmPOjMpRs5GuwxSRB5PLvIC49ITkKNErQPMvDHbY06RRzBFk8YyBUvvyFr9cxT8K4r177chsCDjcUHRr1TYvPklO44EuUbRE+SiUfq2muagJMdj5ioW4HzyXxxsmgEcxx2L6p/SqjYKRGMHrSpZjtdMj+e6GsvVhG+54E0TXhlnZkltLk+t5uVwIDAQAB";

    /**
     * 1. 生成公钥密钥
     *
     * @return 公钥密钥
     */
    @SneakyThrows
    public static KeyStore createKeys() {
        final KeyPairGenerator keyPairGeno = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGeno.initialize(2048);
        final KeyPair keyPair = keyPairGeno.generateKeyPair();

        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        final KeyStore keyStore = new KeyStore();
        keyStore.setPublicKey(Base64.encodeBase64String(publicKey.getEncoded()));
        keyStore.setPrivateKey(Base64.encodeBase64String(privateKey.getEncoded()));
        return keyStore;
    }

    /**
     * 1.1 获取公钥对象，初始化公钥实例
     *
     * @param pubKey {@link String} 公钥字符串
     * @return {@link RSAPublicKey}
     */
    @SneakyThrows
    public static RSAPublicKey getPublicKey(final String pubKey) {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 2.1 公钥加密
     *
     * @param data      {@link String} 明文
     * @param publicKey {@link String} 公钥
     * @return {@link String} 密文
     */
    public static String encryptByPublicKey(final String data, final String publicKey) {
        return encryptByPublicKey(data, getPublicKey(publicKey));
    }

    /**
     * 2.2 公钥加密
     *
     * @param data      {@link String} 明文
     * @param publicKey {@link RSAPublicKey} 公钥
     * @return {@link String} 密文
     */
    @SneakyThrows
    public static String encryptByPublicKey(final String data, final RSAPublicKey publicKey) {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        final byte[] bytes = cipher.doFinal(data.getBytes(UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 3. 通过私钥 byte[] 将公钥还原，适用于RSA算法
     *
     * @param priKey {@link String} 私钥字符串
     * @return {@link RSAPrivateKey}
     */
    @SneakyThrows
    public static RSAPrivateKey getPrivateKey(final String priKey) {
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 4.1 私钥解密
     *
     * @param data       {@link String} 密文
     * @param privateKey {@link String} 私钥
     * @return {@link String} 明文
     */
    public static String decryptByPrivateKey(final String data, final String privateKey) {
        return decryptByPrivateKey(data, getPrivateKey(privateKey));
    }

    /**
     * 4.2 私钥解密
     *
     * @param data       {@link String} 密文
     * @param privateKey {@link RSAPrivateKey} 私钥
     * @return {@link String} 明文
     */
    @SneakyThrows
    public static String decryptByPrivateKey(final String data, final RSAPrivateKey privateKey) {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        final byte[] inputData = Base64.decodeBase64(data);
        final byte[] bytes = cipher.doFinal(inputData);
        return new String(bytes, UTF_8);
    }


    @Getter
    @Setter
    @ToString
    public static class KeyStore {
        /**
         * 公钥
         */
        private String publicKey;
        /**
         * 私钥
         */
        private String privateKey;
    }

}
