package cn.cc.utils.commons.codec;

import cn.cc.utils.commons.lang.RStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * AES加密工具类
 */
@Slf4j
public class JAESUtil {

    public static String DEFAULT_KEY;

    /**
     * 此方法仅spring初始化的时候使用
     * 如果要使用自定义的key，请使用带 key构造的方法
     * @param defaultKey
     */
    public static void setKey(String defaultKey){
        DEFAULT_KEY = defaultKey;
    }

    private static Map<String, Cipher> keyMap = new HashMap<>();

    private static Map<String, Cipher> keyDecryptMap = new HashMap<>();

    private synchronized static Cipher getCipher(String password) {
        if (RStringUtils.isBlank(password)) {
            return null;
        }
        Cipher cipher = keyMap.get(password);
        if (cipher == null) {
            try {
                byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 16);

                SecretKey key = new SecretKeySpec(keyBytes, "AES");

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                keyMap.put(password, cipher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cipher;
    }

    private synchronized static Cipher getDecryptCipher(String password) {
        if (RStringUtils.isBlank(password)) {
            return null;
        }
        Cipher cipher = keyDecryptMap.get(password);
        if (cipher == null) {
            try {
                byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 16);

                SecretKey key = new SecretKeySpec(keyBytes, "AES");

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                keyDecryptMap.put(password, cipher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cipher;
    }

    public static String aes_encrypt(String password){
        return aes_encrypt(password,DEFAULT_KEY);
    }
    public static String aes_encrypt(String password, String strKey) {
        try {
            if (RStringUtils.isBlank(strKey)) {
                return password;
            }
            Cipher cipher = getCipher(strKey);

            byte[] cleartext = password.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            return new String(Hex.encodeHex(ciphertextBytes));
        } catch (Exception e) {
            log.info("aes_encrypt error:" + password);
            e.printStackTrace();
        }
        return null;
    }

    public static String aes_decrypt(String password) {
        return aes_decrypt(password, DEFAULT_KEY);
    }

    public static String aes_decrypt(String password, String strKey) {
        if (RStringUtils.isNotBlank(password) && RStringUtils.startsWith(password, "{AES}")) {
            password = RStringUtils.replace(password, "{AES}", "");
        }
        try {
            Cipher cipher = getDecryptCipher(strKey);

            byte[] cleartext = Hex.decodeHex(password.toCharArray());
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            return new String(ciphertextBytes, "UTF-8");
        } catch (Exception e) {
            log.info("aes_decrypt error:" + password);
            e.printStackTrace();
        }
        return null;
    }

    public static String safe_aes_decrypt(String password, String strKey) {
        if (RStringUtils.isEmpty(password)) {
            return password;
        }
        if (RStringUtils.startsWith(password, "{AES}")) {
            password = RStringUtils.replace(password, "{AES}", "");
        }
        try {
            Cipher cipher = getDecryptCipher(strKey);

            byte[] cleartext = Hex.decodeHex(password.toCharArray());
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            return new String(ciphertextBytes, "UTF-8");

        } catch (Exception e) {
            log.info("safe_aes_decrypt error:" + password);
            e.printStackTrace();
        }
        return null;
    }
//
    /**
     * 返回数据格式为{AES}加密字符串
     *
     * @param str
     * @return
     */
    public static String aesEncryptFormat(String str, String strKey) {
        return "{AES}" + aes_encrypt(str, strKey);
    }

    /**
     * 返回数据格式为{AES}加密字符串 安全
     *
     * @param str
     * @return
     */
    public static String safeEncryptFormat(String str, String strKey) {
        if (RStringUtils.isEmpty(str) || RStringUtils.startsWith(str, "{AES}")) {
            return str;
        }
        return "{AES}" + aes_encrypt(str, strKey);
    }

//    public static void main(String[] args) {
//        String aeskey = "ABCDEFGHIJABCDEF";
//        String str = "abcde";
//        String str_enc = aes_encrypt(str, aeskey);
//        log.info("str_enc: {}", str_enc);
//        String str_dec = aes_decrypt(str_enc, aeskey);
//        log.info("str_dec: {}", str_dec);
//    }

}
