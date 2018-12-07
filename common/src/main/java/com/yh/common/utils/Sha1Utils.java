
package com.yh.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Utils {
    private static final String SHA1 = "SHA-1";

    public Sha1Utils() {
    }

    public static byte[] genSalt(int numBytes) {
        return DigestUtils.genSalt(numBytes);
    }

    public static byte[] sha1(byte[] input) {
        return DigestUtils.digest(input, "SHA-1", (byte[]) null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return DigestUtils.digest(input, "SHA-1", salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return DigestUtils.digest(input, "SHA-1", salt, iterations);
    }

    public static byte[] sha1(InputStream input) throws IOException {
        return DigestUtils.digest(input, "SHA-1");
    }


    public static String MD5(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] array = new byte[0];
        try {
            array = md.digest(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}