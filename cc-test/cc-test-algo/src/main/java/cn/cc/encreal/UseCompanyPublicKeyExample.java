package cn.cc.encreal;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * todo-待测试
 * 对方系统使用你公司公钥的示例（验签+加密）
 */
public class UseCompanyPublicKeyExample {
    // 算法常量
    private static final String RSA_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static void main(String[] args) throws Exception {
        // ====================== 1. 读取你公司的公钥（两种方式任选其一） ======================
        // 方式1：直接读取PEM格式公钥文件（company_public_key.pem）
        PublicKey companyPublicKey = readPublicKeyFromPem("company_public_key.pem");

        // 方式2：从证书文件中读取公钥（更推荐，company_cert.cer）
        // PublicKey companyPublicKey = readPublicKeyFromCert("company_cert.cer");

        // ====================== 2. 场景1：验证你公司发送数据的签名 ======================
        // 模拟你公司发送的原始数据
        String originalData = "订单ID：123456，金额：100元";
        // 模拟你公司用私钥生成的签名（Base64格式）
        String signatureFromCompany = "你公司实际生成的签名字符串";

        // 对方验证签名
        boolean verifyResult = verifySignature(originalData, signatureFromCompany, companyPublicKey);
        System.out.println("签名验证结果：" + (verifyResult ? "✅ 验证通过（数据可信）" : "❌ 验证失败（数据篡改/伪造）"));

        // ====================== 3. 场景2：加密发给你公司的敏感数据 ======================
        // 对方要发给你公司的敏感数据（比如回调参数、隐私信息）
        String sensitiveData = "用户支付密码：123456（仅示例，实际不传输明文密码）";
        // 用你公司公钥加密
        String encryptedData = encryptWithPublicKey(sensitiveData, companyPublicKey);
        System.out.println("用你公司公钥加密后的敏感数据：\n" + encryptedData);

        // 加密后的数据发给你公司，你公司用私钥即可解密
    }

    /**
     * 从PEM格式公钥文件读取公钥
     * @param pemFilePath 你公司的公钥文件路径（company_public_key.pem）
     */
    private static PublicKey readPublicKeyFromPem(String pemFilePath) throws Exception {
        // 读取PEM文件内容，去掉头尾标记
        String pemContent = new String(Files.readAllBytes(Paths.get(pemFilePath)), StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // 去掉换行/空格

        // Base64解码后生成公钥
        byte[] keyBytes = Base64.getDecoder().decode(pemContent);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从证书文件中读取公钥（更安全，推荐）
     * @param certFilePath 你公司的证书文件路径（company_cert.cer）
     */
    private static PublicKey readPublicKeyFromCert(String certFilePath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(Files.newInputStream(Paths.get(certFilePath)));
        // 可选：验证证书有效期（避免使用过期证书）
        cert.checkValidity();
        return cert.getPublicKey();
    }

    /**
     * 用你公司公钥验证签名
     * @param originalData 你公司发送的原始数据
     * @param signature 你公司生成的签名（Base64格式）
     * @param publicKey 你公司的公钥
     */
    private static boolean verifySignature(String originalData, String signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(originalData.getBytes(StandardCharsets.UTF_8));
        // 解码签名并验证
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return sig.verify(signatureBytes);
    }

    /**
     * 用你公司公钥加密敏感数据（只有你公司私钥能解密）
     * @param sensitiveData 对方要发送的敏感数据
     * @param publicKey 你公司的公钥
     */
    private static String encryptWithPublicKey(String sensitiveData, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(sensitiveData.getBytes(StandardCharsets.UTF_8));
        // 转Base64方便传输
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}