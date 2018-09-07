package com.tenzhao.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public final class BlowfishUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String ALGORITHM_NAME = "Blowfish";

    public static String encrypt(String text, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(DigestUtils.md5(key), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(text.getBytes(DEFAULT_CHARSET)));
        } catch (Exception e) {
            throw new IllegalStateException("", e);
        }
    }

    public static String decrypt(String cipherText, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(DigestUtils.md5(key), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] data = Base64.decodeBase64(cipherText);
            return new String(cipher.doFinal(data), DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new IllegalStateException("", e);
        }
    }

    private BlowfishUtils() {
    }
}
