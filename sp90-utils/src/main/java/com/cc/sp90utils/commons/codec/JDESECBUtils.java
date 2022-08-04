/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-22 14:29
 * Copyright
 */

package com.cc.sp90utils.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

@Slf4j
public class JDESECBUtils {

    private static final String ALGORITHM = "DES";
    private static final String ENCODE = "utf-8";
    private static final String MODE_ECB_PKCS5Padding = "DES/ECB/PKCS5Padding";

    /* 设置默认key */
    private static final String defaultKey = "ThmC40Hw5dvTuXiirGjEP9jm";

    /**
     * 生成一个测试key
     *
     * @return 测试key
     */
    public static String randomKey() {
        return RandomStringUtils.randomAlphanumeric(24);
    }

    /**
     * 1. 加密
     *
     * @param data 值
     * @param key  key
     * @return 加密后的结果
     */
    public static String encrypt(String data, String key) {
        byte[] bt;
        String encodeData = "";
        try {
            bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
            //字节转化为16进制字符串
            encodeData = Hex.encodeHexString(bt);
        } catch (Exception e) {
            log.error("----des encrypt error", e);
            //throw new RuntimeException()
        }

        return encodeData;
    }

    /**
     * 1.1 根据键值加密
     *
     * @param data 值
     * @param key  key
     * @return 加密后的数组
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(MODE_ECB_PKCS5Padding);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * 2. 解密
     *
     * @param data 数据
     * @param key  key
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) {
        String decryptString = "";
        try {
            byte[] bt = decrypt(Hex.decodeHex(data), key.getBytes(ENCODE));
            decryptString = new String(bt, ENCODE);
        } catch (Exception e) {
            log.error("---decrypt error", e);
        }
        return decryptString;
    }

    /**
     * 2.1 解密
     *
     * @param data 数据处理为byte
     * @param key  key处理为byte
     * @return 解密后的值
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(MODE_ECB_PKCS5Padding);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

}
