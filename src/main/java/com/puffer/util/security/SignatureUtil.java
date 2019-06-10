package com.puffer.util.security;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 对称加密工具类
 *
 * @author buyi
 * @date 2019年06月10日 17:02:31
 * @since 1.0.0
 */
public class SignatureUtil {
    private static final String RSA = "RSA";

    private static final String SHA1WITHRSA = "SHA1WithRSA";

    private static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");

    /**
     * 获取公钥字符串
     *
     * @param keyPair
     * @return java.lang.String
     * @author buyi
     * @date 2019年06月10日 17:06:10
     * @since 1.0.0
     */
    public static String generatePublicKey(KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }

    /**
     * 获取私钥字符串
     *
     * @param keyPair
     * @return java.lang.String
     * @author buyi
     * @date 2019年06月10日 17:06:40
     * @since 1.0.0
     */
    public static String generatePrivateKey(KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }

    /**
     * 生成RSA keyPair
     *
     * @param keySize
     * @return java.security.KeyPair
     * @author buyi
     * @date 2019年06月10日 17:04:36
     * @since 1.0.0
     */
    public static KeyPair generateRsaKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = null;
        keyPairGen = KeyPairGenerator.getInstance(RSA);

        keyPairGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();

        return keyPair;
    }

    /**
     * 根据密钥字符串获取密钥
     *
     * @param privateKeyStr
     * @return java.security.PrivateKey
     * @author buyi
     * @date 2019年06月10日 17:12:52
     * @since 1.0.0
     */
    public static PrivateKey getRsaPkcs8PrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Base64.decodeBase64(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }

    /**
     * 根据公钥字符串获取公钥
     *
     * @param publicKeyStr
     * @return java.security.PublicKey
     * @author buyi
     * @date 2019年06月10日 17:14:17
     * @since 1.0.0
     */
    private static PublicKey getRsaX509PublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytes = Base64.decodeBase64(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
    }

    /**
     * 给字符串签名
     *
     * @param privateKeyStr 私钥字符串
     * @param data          待签名的字符串
     * @return java.lang.String
     * @author buyi
     * @date 2019年06月10日 17:17:03
     * @since 1.0.0
     */
    public static String sign(String privateKeyStr, String data) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        PrivateKey privateKey = getRsaPkcs8PrivateKey(privateKeyStr);

        byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);

        ByteArrayInputStream input = new ByteArrayInputStream(dataBytes);

        Signature signature = Signature.getInstance(SHA1WITHRSA);
        signature.initSign(privateKey);

        doUpdate(signature, input);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验签名
     *
     * @param publicKeyStr 公钥串
     * @param data         待检验签名串
     * @param sign         签名值
     * @return boolean
     * @author buyi
     * @date 2019年06月10日 17:31:38
     * @since 1.0.0
     */
    public static boolean verify(String publicKeyStr, String data, String sign) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, InvalidKeySpecException {
        PublicKey publicKey = getRsaX509PublicKey(publicKeyStr);

        byte[] signBytes = Base64.decodeBase64(sign);

        byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);

        ByteArrayInputStream input = new ByteArrayInputStream(dataBytes);
        Signature signature = Signature.getInstance(SHA1WITHRSA);

        signature.initVerify(publicKey);

        doUpdate(signature, input);

        return signature.verify(signBytes);
    }

    private static void doUpdate(Signature signature, InputStream input) throws IOException, SignatureException {

        byte[] buf = new byte[4096];

        int c = 0;
        do {
            c = input.read(buf);

            if (c > 0) {
                signature.update(buf, 0, c);
            }
        } while (c != -1);
    }

}
