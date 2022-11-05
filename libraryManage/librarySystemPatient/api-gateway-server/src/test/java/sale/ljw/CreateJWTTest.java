package sale.ljw;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.jwt.JwtHelper;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CreateJWTTest {

    @Test
    public void createJWT(){
        //创建密钥工厂
        //1,指定私钥位置
        ClassPathResource classPathResource=new ClassPathResource("librarySystem.jks");
        //2,指定私钥库的密码
        String keyPass="librarySystem";
        KeyStoreKeyFactory keyStoreKeyFactory=new KeyStoreKeyFactory(classPathResource,keyPass.toCharArray());
        //基于工厂获取私钥
        //1,密钥别名
        String alias="librarySystem";
        //2,密钥密码
        String password="librarySystem";
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, password.toCharArray());
        //将当前私钥转化成rsa类型
        RSAPrivateKey rsaPrivateKey= (RSAPrivateKey) keyPair.getPrivate();
        //生成JWT令牌
        Map<String,Object> map = new HashMap<>();
        map.put("company", "ljw");
        map.put("address", "hangzhou");
        Jwt encode = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String encoded = encode.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void parseJwt(){
        //基于公钥解析jwt
        String jwt="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiaGFuZ3pob3UiLCJjb21wYW55IjoibGp3In0.ENA7W6F6AWvl_swBfv_0Sd5KAELBTHw4SFG7lU14MW8_3EIBMGf5WMYp4fcXSAXH6EeraZdHeYICzpDbcWiOGYnNaKvYzUtkiqC3EDYT_MesjE3ihMroqOa4CMpTkq72N0yCeuoafV70IOnqAhUAn8m0i5DH55G5WlF6eUfY5HIjSiQj94kYKFGc8mOWRwgrLfBMC-YVJmCeCElHS13ODd3WxtHIj1N4pS1FVzNOCgpcrJfzt2ZXeohA3Jbw8JBQvPuMY90BadkXr7u3sUJnXMoLGb32ZQHtkGMhIkdO6tBu8HOkIKEsWd4XIrs6GM6gv7ca6t85ExUkzAAbFs_qSQ";
        String publicKey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgtw6z5+94RpIulYkuWZcjeTa9wI4hm/DLZ8nGaW3mZAV7aHCxqtPmMVjCz+kX+kbhPlL13ShnAYL0rVykjSBGI7TkpbhX5XcWY9Wp2vbKtyNogIey+eSj+MN0xH7yDkJbFVaq6nW11cSZf0Ttm2V9U6by7uDBTf7ZDmimvyzgQw9t2v1Ng+JB+pENzt15y5Ip0lcz74W+lIh7y6mq6Jdtj5dCGsSr+aOX9uSEeAqZknzIBS0o9j2OEM12gWnrNPHjTbpXQsOLP+TeJIGGT2fCfQ0r6ylK0sSbDQtqEbDVUrzHaLHix1/vP/adLa3iAL1loZq2UG13awVltfrLrcCMwIDAQAB-----END PUBLIC KEY-----";
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        String claims = token.getClaims();
        System.out.println(claims);
    }

}
