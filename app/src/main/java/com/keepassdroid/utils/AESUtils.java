package com.keepassdroid.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密
 *
 *
 * @date:2016年10月19日
 */
public class AESUtils {

    // AES加密密钥算法
    private static final String ALGORITHM = "AES";

    /**
     * AES解密
     *
     *
     * @date : 2016年10月19日
     *
     * @param encryptValue
     *            待解密内容
     * @param key
     *            秘钥
     * @return
     * @throws Exception
     */
    public static byte[]  decrypt(byte[] encryptValue, byte[] key) throws Exception {
        return aesDecryptByBytesToByte(encryptValue, key);
    }

    /**
     * AES加密
     *
     *
     * @date : 2016年10月19日
     *
     * @param value
     *            待加密内容
     * @param key
     *            秘钥
     * @return
     * @throws Exception
     */
    public static byte[]  encrypt(byte[] value, byte[] key) throws Exception {
        return aesEncryptToBytes(value, key);
    }

    private static byte[] aesEncryptToBytes(byte[] contentByte, byte[] encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(AESUtils.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKeyByte(encryptKey), AESUtils.ALGORITHM));
        return cipher.doFinal(contentByte);
    }

    private static byte[] aesDecryptByBytesToByte(byte[] encryptBytes, byte[] decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(AESUtils.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKeyByte(decryptKey), AESUtils.ALGORITHM));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return decryptBytes;
    }

    private static byte[] getKeyByte(byte[] key) throws UnsupportedEncodingException {
        byte[] key_byte = new byte[16];
        System.arraycopy(key, 0, key_byte, 0, key.length);
        return key_byte;
    }

}
