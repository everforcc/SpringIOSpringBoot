package javax.crypto;

public class DESJDKUtil {

    private static final String ALGORITHM = "DES";

    /**
     * 生成密钥
     *
     * @return 密钥
     * @throws Exception
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(56); // 56 bit key for DES
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    /**
     * 加密
     *
     * @param key  key
     * @param text 待加密参数
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encrypt(SecretKey key, byte[] text) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text);
    }

    /**
     * 解密
     *
     * @param key  key
     * @param text 加密后数据
     * @return 解密后数据
     * @throws Exception
     */
    public static byte[] decrypt(SecretKey key, byte[] text) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(text);
    }

//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder hex = new StringBuilder();
//        for (byte b : bytes) {
//            hex.append(String.format("%02X", b));
//        }
//        return hex.toString();
//    }

}
