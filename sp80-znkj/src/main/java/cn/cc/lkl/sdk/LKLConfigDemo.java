package cn.cc.lkl.sdk;

import cn.cc.lkl.dto.CommonRequestDTO;
import cn.cc.lkl.util.CertUtil;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.Config;
import com.lkl.laop.sdk.Config2;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V2LaepCreateEleReceiptRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LKLConfigDemo {

    /**
     * 接入方唯一编号
     * 拉卡拉开放平台进行配置开通
     */
    private static final String appId = "OP00000003";


    /**
     * 商户证书序列号,和商户私钥对应
     * 00dfba8194c41b84cf
     * 1745381c327
     */
    private static final String serialNo = "1745381c327";


    /**
     * 商户私钥文件路径
     * 商户私钥地址,用于请求签名
     */
    private static final String priKeyPath = "D:/cache/BaiduSyncdisk/znkj/project/拉卡拉/文档/sdk/V3商户测试私钥、公钥证书/OP00000003_private_key.pem";


    /**
     * 无需配置
     * OP00000003_cert.cer
     */

    /**
     * 拉卡拉公钥证书地址,用于验签
     */
    private static final String lklCerPath = "D:/cache/BaiduSyncdisk/znkj/project/拉卡拉/文档/sdk/V3商户测试私钥、公钥证书/lkl-apigw-v2.cer";


    /**
     * 拉卡拉支付平台证书地址2(用于拉卡拉通知验签，当前同lklCerPath)
     */
    private static final String lklNotifyCerPath = lklCerPath;

    /**
     * 拉卡拉开放平台服务地址
     * 测试 https://test.wsmsd.cn/sit
     * 生产 https://s2.lakala.com
     */
    public static final String serverUrl = "https://test.wsmsd.cn/sit";


    private static final String sm4Key = "如果需要密文传输请申请拉卡拉SM4密钥";

    // OP00000003_cert.cer 给lkl配置

    /**
     * 商户私钥字符串,用于请求签名
     * OP00000003_private_key.pem
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
     * lkl-apigw-v2.cer
     */
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
    private static final String lklCerLineStr = "-----BEGIN CERTIFICATE----- MIIEMTCCAxmgAwIBAgIGAXRTgcMnMA0GCSqGSIb3DQEBCwUAMHYxCzAJBgNVBAYTAkNOMRAwDgYDVQQIDAdCZWlKaW5nMRAwDgYDVQQHDAdCZWlKaW5nMRcwFQYDVQQKDA5MYWthbGEgQ28uLEx0ZDEqMCgGA1UEAwwhTGFrYWxhIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBMB4XDTIwMTAxMDA1MjQxNFoXDTMwMTAwODA1MjQxNFowZTELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JlaUppbmcxEDAOBgNVBAcMB0JlaUppbmcxFzAVBgNVBAoMDkxha2FsYSBDby4sTHRkMRkwFwYDVQQDDBBBUElHVy5MQUtBTEEuQ09NMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1zHL54HiI8d2sLJlwoQji3/ln0nsvfZ/XVpOjuB+1YR6/0LdxEDMC/hxI6iH2Rm5MjwWz3dmN/6BZeIgwGeTOWJUZFARo8UduKrlhC6gWMRpAiiGC8wA8stikc5gYB+UeFVZi/aJ0WN0cpPJYCvPBhxhMvhVDnd4hNohnR1L7k0ypuWg0YwGjC25FaNAEFBYP9EYUyCJjE//9Z7sMzHR9SJYCqqo6r9bOH9G6sWKuEp+osuAh+kJIxJMHfipw7w3tEcWG0hce9u/el4cYJtg8/PPMVoccKmeCzMvarr7jdKP4lenJbtwlgyfs+JgNu60KMUJH8RS72wC9NYuFz09wIDAQABo4HVMIHSMIGSBgNVHSMEgYowgYeAFCnH4DkZPR6CZxRn/kIqVsModJHpoWekZTBjMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQmVpSmluZzEQMA4GA1UEBwwHQmVpSmluZzEXMBUGA1UECgwOTGFrYWxhIENvLixMdGQxFzAVBgNVBAMMDkxha2FsYSBSb290IENBggYBaiUALIowHQYDVR0OBBYEFJ2Kx9YZfmWpkKFnC33C0r5DK3rFMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMA0GCSqGSIb3DQEBCwUAA4IBAQBZoeU0XyH9O0LGF9R+JyGwfU/O5amoB97VeM+5n9v2z8OCiIJ8eXVGKN9Ltl9QkpTEanYwK30KkpHcJP1xfVkhPi/cCMgfTWQ5eKYC7Zm16zk7n4CP6IIgZIqmTVGsIGKk8RzWseyWPB3lfqMDR52V1tdA1S8lJ7a2Xnpt5M2jkDXoArl3SVSwCb4DAmThYhak48M++fUJNYII9JBGRdRGbfJ2GSFdPXgesUL2CwlReQwbW4GZkYGOg9LKCNPK6XShlNdvgPv0CCR08KCYRwC3HZ0y1F0NjaKzYdGNPrvOq9lA495ONZCvzYDogmsu/kd6eqxTs/JwdaIYr4sCMg8Z -----END CERTIFICATE-----";


    /**
     * 拉卡拉支付平台证书字符串2(用于拉卡拉通知验签，当前同lklCerStr)
     * lkl-apigw-v2.cer
     */
    private static final String lklNotifyCerStr = "-----BEGIN CERTIFICATE-----\n" +
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
    private static final String lklNotifyCerLineStr = "-----BEGIN CERTIFICATE----- MIIEMTCCAxmgAwIBAgIGAXRTgcMnMA0GCSqGSIb3DQEBCwUAMHYxCzAJBgNVBAYTAkNOMRAwDgYDVQQIDAdCZWlKaW5nMRAwDgYDVQQHDAdCZWlKaW5nMRcwFQYDVQQKDA5MYWthbGEgQ28uLEx0ZDEqMCgGA1UEAwwhTGFrYWxhIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBMB4XDTIwMTAxMDA1MjQxNFoXDTMwMTAwODA1MjQxNFowZTELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JlaUppbmcxEDAOBgNVBAcMB0JlaUppbmcxFzAVBgNVBAoMDkxha2FsYSBDby4sTHRkMRkwFwYDVQQDDBBBUElHVy5MQUtBTEEuQ09NMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1zHL54HiI8d2sLJlwoQji3/ln0nsvfZ/XVpOjuB+1YR6/0LdxEDMC/hxI6iH2Rm5MjwWz3dmN/6BZeIgwGeTOWJUZFARo8UduKrlhC6gWMRpAiiGC8wA8stikc5gYB+UeFVZi/aJ0WN0cpPJYCvPBhxhMvhVDnd4hNohnR1L7k0ypuWg0YwGjC25FaNAEFBYP9EYUyCJjE//9Z7sMzHR9SJYCqqo6r9bOH9G6sWKuEp+osuAh+kJIxJMHfipw7w3tEcWG0hce9u/el4cYJtg8/PPMVoccKmeCzMvarr7jdKP4lenJbtwlgyfs+JgNu60KMUJH8RS72wC9NYuFz09wIDAQABo4HVMIHSMIGSBgNVHSMEgYowgYeAFCnH4DkZPR6CZxRn/kIqVsModJHpoWekZTBjMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQmVpSmluZzEQMA4GA1UEBwwHQmVpSmluZzEXMBUGA1UECgwOTGFrYWxhIENvLixMdGQxFzAVBgNVBAMMDkxha2FsYSBSb290IENBggYBaiUALIowHQYDVR0OBBYEFJ2Kx9YZfmWpkKFnC33C0r5DK3rFMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMA0GCSqGSIb3DQEBCwUAA4IBAQBZoeU0XyH9O0LGF9R+JyGwfU/O5amoB97VeM+5n9v2z8OCiIJ8eXVGKN9Ltl9QkpTEanYwK30KkpHcJP1xfVkhPi/cCMgfTWQ5eKYC7Zm16zk7n4CP6IIgZIqmTVGsIGKk8RzWseyWPB3lfqMDR52V1tdA1S8lJ7a2Xnpt5M2jkDXoArl3SVSwCb4DAmThYhak48M++fUJNYII9JBGRdRGbfJ2GSFdPXgesUL2CwlReQwbW4GZkYGOg9LKCNPK6XShlNdvgPv0CCR08KCYRwC3HZ0y1F0NjaKzYdGNPrvOq9lA495ONZCvzYDogmsu/kd6eqxTs/JwdaIYr4sCMg8Z -----END CERTIFICATE-----";

    public static void main(String[] args) {
        try {
            demoConfig4();
            demoOrganizationParentCode(serverUrl);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥私钥地址文件
     *
     * @throws SDKException
     */
    public static boolean demoConfig1() throws SDKException {
        //方式1：
        return LKLSDK.init(new Config(appId, serialNo, priKeyPath, lklCerPath, lklNotifyCerPath, serverUrl));
    }

    /**
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
     * 密钥字符串的形式，用这种
     */
    public static boolean demoConfig4() throws SDKException {
        //方式4：
        Config2 config = new Config2();
        config.setAppId(appId);
        config.setSerialNo(serialNo);
        config.setPriKey(CertUtil.convertToStandardFormat(priKeyLineStr));
        config.setLklCer(CertUtil.convertToStandardFormat(lklCerStr));
        config.setLklNotifyCer(CertUtil.convertToStandardFormat(lklCerLineStr));
//        config.setPriKey(priKeyStr);
//        config.setLklCer(lklNotifyCerStr);
//        config.setLklNotifyCer(lklNotifyCerStr);
        config.setServerUrl(serverUrl);
//        config.setSm4Key(sm4Key);
        return LKLSDK.init(config);
    }

    public static void demoOrganizationParentCode(String serverUrl) throws SDKException {
        CommonRequestDTO commRequest = new CommonRequestDTO();

        JSONObject reqData = new JSONObject();
        reqData.put("parent_code", "991000");
        reqData.put("org_code", 1951582);
        commRequest.setReq_data(reqData);
        String response = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/organization_parent_code", JSONObject.toJSONString(commRequest));

        log.info("response: \r\n{}", response);
    }

    /**
     * 官方给的示例请求
     *
     * @throws SDKException
     */
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


}
