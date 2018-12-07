package com.puffer.util.security;

import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.*;

public class AESUtilTest {

    @Test
    public void testEncode() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String str = "孙二";
        String key = "80e36e39f34e678c";
        String encodeStr = AESUtil.encode(key, str);
        System.out.println("加密后：" + encodeStr);
        String decodeStr = AESUtil.decode(key, encodeStr);
        assertEquals(str, decodeStr);
    }

}