package cn.cc.encreal;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * todo-待测试
 * Java生成RSA密钥对+自签证书（需引入BouncyCastle依赖）
 * 依赖：Maven引入BouncyCastle（版本根据需求调整）
 */
public class CompanyCertGenerator {
    // 算法配置
    private static final String RSA_ALGORITHM = "RSA";
    private static final int RSA_KEY_SIZE = 2048;
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String PROVIDER = "BC"; // BouncyCastle加密提供者

    static {
        // 注册BouncyCastle提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        // 1. 生成RSA密钥对
        KeyPair keyPair = generateRSAKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("✅ RSA密钥对生成完成");

        // 2. 生成自签证书（替换为公司实际信息）
        X509Certificate cert = generateSelfSignedCert(
                keyPair,
                "CN=EVERFORCC, O=CC, C=CN", // 公司信息（X.500格式）
                365 * 3 // 证书有效期（3年）
        );
        System.out.println("✅ 自签证书生成完成");

        // 3. 保存密钥和证书到文件（PEM格式，方便部署）
        savePemFile("company_private_key.pem", "PRIVATE KEY", privateKey);
        savePemFile("company_public_key.pem", "PUBLIC KEY", publicKey);
        savePemFile("company_cert.cer", "CERTIFICATE", cert);
        System.out.println("✅ 密钥和证书已保存到当前目录");
    }

    /**
     * 生成RSA密钥对
     */
    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM, PROVIDER);
        keyGen.initialize(RSA_KEY_SIZE, new SecureRandom());
        return keyGen.generateKeyPair();
    }

    /**
     * 生成自签X509证书
     * @param keyPair 密钥对（用私钥签名，公钥嵌入证书）
     * @param subjectInfo 公司信息（X.500格式，如CN=公司名, O=组织, C=国家）
     * @param validDays 证书有效期（天）
     */
    private static X509Certificate generateSelfSignedCert(KeyPair keyPair, String subjectInfo, int validDays) throws Exception {
        // 证书基础信息
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + validDays * 24 * 60 * 60 * 1000L);
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis()); // 唯一序列号

        // 构建证书
        X500Name issuer = new X500Name(subjectInfo); // 自签证书：签发者=使用者
        X500Name subject = issuer;
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer, serialNumber, startDate, endDate, subject, keyPair.getPublic()
        );

        // 用私钥签名证书（模拟CA签名）
        ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                .setProvider(PROVIDER)
                .build(keyPair.getPrivate());

        // 生成证书
        return new JcaX509CertificateConverter()
                .setProvider(PROVIDER)
                .getCertificate(certBuilder.build(signer));
    }

    /**
     * 保存密钥/证书为PEM格式文件（带头尾标记，方便部署）
     * @param fileName 文件名
     * @param pemType PEM类型（如PRIVATE KEY、PUBLIC KEY、CERTIFICATE）
     * @param obj 要保存的对象（PrivateKey/PublicKey/X509Certificate）
     */
    private static void savePemFile(String fileName, String pemType, Object obj) throws Exception {
        try (JcaPEMWriter pemWriter = new JcaPEMWriter(new FileWriter("D:/cache/test/RSA/"+fileName))) {
            pemWriter.writeObject(obj);
            pemWriter.flush();
        }
    }
}
