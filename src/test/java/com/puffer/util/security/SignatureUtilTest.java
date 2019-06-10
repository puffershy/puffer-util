package com.puffer.util.security;

import org.testng.annotations.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import static org.testng.Assert.assertTrue;

public class SignatureUtilTest {

    @Test
    public void test() throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException, IOException {
        int keySize = 2048;

        String data = "测试数据源";

        KeyPair keyPair = SignatureUtil.generateRsaKeyPair(keySize);

        String privateKey = SignatureUtil.generatePrivateKey(keyPair);
        String publicKey = SignatureUtil.generatePublicKey(keyPair);
        System.out.println("公钥串:"+publicKey);
        System.out.println("私钥串:"+privateKey);

        String sign = SignatureUtil.sign(privateKey, data);
        System.out.println("签名串:"+sign);

        boolean verify = SignatureUtil.verify(publicKey, data, sign);

        assertTrue(verify);

    }

}