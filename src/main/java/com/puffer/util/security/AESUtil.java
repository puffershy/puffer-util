package com.puffer.util.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES对称加密
 *
 * @author buyi
 * @date 2018年11月22日 17:00:01
 * @since 1.0.0
 */
public class AESUtil {

    /**
     * 加密
     *
     * @param key 密钥串
     * @param str 待加密的字符串
     * @return java.lang.String
     * @author buyi
     * @date 2018年11月22日 17:06:51
     * @since 1.0.0
     */
    public static String encode(String key, String str)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("参数不能为空，key 或者 str");
        }
        Cipher cipher = Cipher.getInstance("AES");

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] doFinal = cipher.doFinal(str.getBytes(CharEncoding.UTF_8));
        return Base64.encodeBase64String(doFinal);
    }

    /**
     * 解密
     *
     * @param key 密钥串
     * @param str 待解密字符串
     * @return java.lang.String
     * @author buyi
     * @date 2018年11月22日 17:10:24
     * @since 1.0.0
     */
    public static String decode(String key, String str)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("参数不能为空，key 或者 str");
        }

        Cipher cipher = Cipher.getInstance("AES");

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] doFinal = cipher.doFinal(Base64.decodeBase64(str));
        return new String(doFinal, CharEncoding.UTF_8);
    }
}
