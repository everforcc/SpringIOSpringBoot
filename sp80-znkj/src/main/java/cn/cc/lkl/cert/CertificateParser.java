package cn.cc.lkl.cert;

import cn.cc.lkl.util.CertUtil;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class CertificateParser {

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

    private static final String lklCerStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIEMTCCAxmgAwIBAgIGAXRTgcMnMA0GCSqGSIb3DQEBCwUAMHYxCzAJBgNVBAYT\n" +
            "AkNOMRAwDgYDVQQIDAdCZWlKaW5nMRAwDgYDVQQHDAdCZWlKaW5nMRcwFQYDVQQK\n" +
            "DA5MYWthbGEgQ28uLEx0ZDEqMCgGA1UEAwwhTGFrYWxhIE9yZ2FuaXphdGlvbiBW\n" +
            "YWxpZGF0aW9uIENBMB4XDTIwMTAxMDA1MjQxNFoXDTMwMTAwODA1MjQxNFowZTEL\n" +
            "MAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JlaUppbmcxEDAOBgNVBAcMB0JlaUppbmcx\n" +
            "FzAVBgNVBAoMDkxha2FsYSBDby4sTHRkMRkwFwYDVQQDDBBBUElHVy5MQUtBTEEu\n" +
            "Q09NMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1zHL54HiI8d2sLJ\n" +
            "lwoQji3/ln0nsvfZ/XVpOjuB+1YR6/0LdxEDMC/hxI6iH2Rm5MjwWz3dmN/6BZeI\n" +
            "gwGeTOWJUZFARo8UduKrlhC6gWMRpAiiGC8wA8stikc5gYB+UeFVZi/aJ0WN0cpP\n" +
            "JYCvPBhxhMvhVDnd4hNohnR1L7k0ypuWg0YwGjC25FaNAEFBYP9EYUyCJjE//9Z7\n" +
            "sMzHR9SJYCqqo6r9bOH9G6sWKuEp+osuAh+kJIxJMHfipw7w3tEcWG0hce9u/el4\n" +
            "cYJtg8/PPMVoccKmeCzMvarr7jdKP4lenJbtwlgyfs+JgNu60KMUJH8RS72wC9NY\n" +
            "uFz09wIDAQABo4HVMIHSMIGSBgNVHSMEgYowgYeAFCnH4DkZPR6CZxRn/kIqVsMo\n" +
            "dJHpoWekZTBjMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQmVpSmluZzEQMA4GA1UE\n" +
            "BwwHQmVpSmluZzEXMBUGA1UECgwOTGFrYWxhIENvLixMdGQxFzAVBgNVBAMMDkxh\n" +
            "a2FsYSBSb290IENBggYBaiUALIowHQYDVR0OBBYEFJ2Kx9YZfmWpkKFnC33C0r5D\n" +
            "K3rFMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMA0GCSqGSIb3DQEBCwUA\n" +
            "A4IBAQBZoeU0XyH9O0LGF9R+JyGwfU/O5amoB97VeM+5n9v2z8OCiIJ8eXVGKN9L\n" +
            "tl9QkpTEanYwK30KkpHcJP1xfVkhPi/cCMgfTWQ5eKYC7Zm16zk7n4CP6IIgZIqm\n" +
            "TVGsIGKk8RzWseyWPB3lfqMDR52V1tdA1S8lJ7a2Xnpt5M2jkDXoArl3SVSwCb4D\n" +
            "AmThYhak48M++fUJNYII9JBGRdRGbfJ2GSFdPXgesUL2CwlReQwbW4GZkYGOg9LK\n" +
            "CNPK6XShlNdvgPv0CCR08KCYRwC3HZ0y1F0NjaKzYdGNPrvOq9lA495ONZCvzYDo\n" +
            "gmsu/kd6eqxTs/JwdaIYr4sCMg8Z\n" +
            "-----END CERTIFICATE-----\n";

    private static String lklCerLineStr = "-----BEGIN CERTIFICATE----- MIIDYTCCAkmgAwIBAgIJAN+6gZTEG4TPMA0GCSqGSIb3DQEBCwUAMEkxCzAJBgNVBAYTAlVTMREwDwYDVQQIEwhzaGFuZ2hhaTERMA8GA1UEBxMIc2hhbmdoYWkxFDASBgNVBAMUC2xha2FsYV8yMDIxMB4XDTIxMDYxODA3MjEzNFoXDTMxMDYxOTA3MjEzNFowSTELMAkGA1UEBhMCVVMxETAPBgNVBAgTCHNoYW5naGFpMREwDwYDVQQHEwhzaGFuZ2hhaTEUMBIGA1UEAxQLbGFrYWxhXzIwMjEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDvDBZyHUDndAGxrIcsCV2njhNO3vCEZotTaWYSYwtDvkcAb1EjsBFabXZaKigpqFXk5XXNI3NIHP9M8XKzIgGvc65NpLAfRjVql8JiTvLyYd1gIUcOXMInabu+oX7dQSI1mS8XzqaoVRhDZQWhXcJW9bxMulgnzvk0Ggw07AjGF7si+hP/Va8SJmN7EJwfQq6TpSxR+WdIHpbWdhZ+NHwitnQwAJTLBFvfk28INM39G7XOsXdVLfsooFdglVTOHpNuRiQAj9gShCCNrpGsNQxDiJIxE43qRsNsRwigyo6DPJk/klgDJa417E2wgP8VrwiXparO4FMzOGK15quuoD7DAgMBAAGjTDBKMAkGA1UdEwQCMAAwEQYJYIZIAYb4QgEBBAQDAgTwMAsGA1UdDwQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwDQYJKoZIhvcNAQELBQADggEBAI21YYAlH+Pc1ISvnbQrGqL8suGL0Hh/8hGaFfrJEJEKr9OeC8jElUhck2MTmfu/Y1lB7r8RBrhGPXi4kTXmB6ADs/9+ezNW3WXyFj7fhs3JcZ3mo33T9wyQySDKd//JrEtrTsc/s2PZ602yqNmPomXSzjrlugaMyC7LI9sR44mc7sQnchjHoxrQFD5/usTFW72UQfYCORsQWYMt0KKEyAcpRL51RE3xbX1WDtduFYGP62PbwLAn2nCL/j1wlF5hltWj7sditWqKgso5F8BTffn2Bb0RdsNxqwMy1cTPrWLeXVOqMDu3ge7hvoav8lZKTjk5Kmqhs7wNAQXKmg9qSwo= -----END CERTIFICATE-----";

    public static void main(String[] args) throws Exception {
        System.out.println("=== 证书转换和解析测试 ===");
        
        // 测试1：证书转换
        System.out.println("测试1：证书转换");
        System.out.println("步骤1：将多行证书转换为单行格式（用于存储）");
        String singleLineCert = CertUtil.convertToSingleLine(lklCerStr);
        System.out.println("单行格式：");
        System.out.println(singleLineCert);
        
        System.out.println("\n步骤2：将单行格式转换为标准格式（用于解析）");
        String standardCert = CertUtil.convertToStandardFormat(singleLineCert);
        System.out.println("标准格式：");
        System.out.println(standardCert);
        
        System.out.println("\n步骤3：解析证书");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(
                new ByteArrayInputStream(standardCert.getBytes(StandardCharsets.UTF_8))
        );
        
        System.out.println("证书解析成功！");
        System.out.println("主体：" + cert.getSubjectDN());
        System.out.println("颁发者：" + cert.getIssuerDN());
        System.out.println("有效期至：" + cert.getNotAfter());
        
        // 测试2：私钥转换
        System.out.println("\n\n=== 私钥转换和解析测试 ===");
        System.out.println("步骤1：将多行私钥转换为单行格式（用于存储）");
        String singleLinePriKey = CertUtil.convertToSingleLine(priKeyStr);
        System.out.println("单行格式：");
        System.out.println(singleLinePriKey);
        
        System.out.println("\n步骤2：将单行格式转换为标准格式（用于解析）");
        String standardPriKey = CertUtil.convertToStandardFormat(singleLinePriKey);
        System.out.println("标准格式：");
        System.out.println(standardPriKey);
        
        System.out.println("\n步骤3：解析私钥");
        try {
            // 注意：私钥不能直接用CertificateFactory解析，这里只是演示格式转换
            System.out.println("私钥格式转换成功！");
            System.out.println("私钥类型：PRIVATE KEY");
            System.out.println("私钥长度：" + standardPriKey.length() + " 字符");
        } catch (Exception e) {
            System.out.println("私钥解析失败：" + e.getMessage());
        }
    }



}
