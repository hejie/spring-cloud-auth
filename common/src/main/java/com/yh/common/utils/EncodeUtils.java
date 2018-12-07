
package com.yh.common.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class EncodeUtils {
    public static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static List<Pattern> xssPatterns = Arrays.asList(new Pattern[]{Pattern.compile("(<\\s*(script|link|style|iframe)([\\s\\S]*?)(>|<\\/\\s*\\1\\s*>))|(</\\s*(script|link|style|iframe)\\s*>)", 2), Pattern.compile("\\s*(href|src)\\s*=\\s*(\"\\s*(javascript|vbscript):[^\"]+\"|'\\s*(javascript|vbscript):[^']+'|(javascript|vbscript):[^\\s]+)\\s*(?=>)", 2), Pattern.compile("\\s*on[a-z]+\\s*=\\s*(\"[^\"]+\"|'[^']+'|[^\\s]+)\\s*(?=>)", 2), Pattern.compile("(eval\\((.|\\n)*\\)|xpression\\((.|\\n)*\\))", 2)});
    private static Pattern sqlPattern = Pattern.compile("(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)", 2);
    public static final int HASH_INTERATIONS = 1024;

    public EncodeUtils() {
    }

    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];

        for (int i = 0; i < input.length; ++i) {
            chars[i] = BASE62[(input[i] & 255) % BASE62.length];
        }

        return new String(chars);
    }

    public static String encodeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    public static String decodeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    public static String encodeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

    public static String decodeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String encodeUrl(String part) {
        return encodeUrl(part, "UTF-8");
    }

    public static String encodeUrl(String part, String encoding) {
        if (part == null) {
            return null;
        } else {
            try {
                return URLEncoder.encode(part, encoding);
            } catch (UnsupportedEncodingException var3) {
                throw ExceptionUtils.unchecked(var3);
            }
        }
    }

    public static String decodeUrl(String part) {
        return decodeUrl(part, "UTF-8");
    }

    public static String decodeUrl(String part, String encoding) {
        if (part == null) {
            return null;
        } else {
            try {
                return URLDecoder.decode(part, encoding);
            } catch (UnsupportedEncodingException var3) {
                throw ExceptionUtils.unchecked(var3);
            }
        }
    }


    public static String encryptPassword(String plainPassword) {
        String a = EncodeUtils.decodeHtml(plainPassword);
        byte[] b = Sha1Utils.genSalt(8);
        byte[] c = Sha1Utils.sha1(a.getBytes(), b, 1024);
        return (new StringBuilder()).insert(0, EncodeUtils.encodeHex(b)).append(EncodeUtils.encodeHex(c)).toString();
    }


    /**
     * 验证密码正确性
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        try {
            String plain = EncodeUtils.decodeHtml(plainPassword);
            byte[] salt = EncodeUtils.decodeHex(password.substring(0, 16));
            byte[] hashPassword = Sha1Utils.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
            return password.equals(EncodeUtils.encodeHex(salt) + EncodeUtils.encodeHex(hashPassword));
        } catch (Exception e) {
            return false;
        }
    }


    public static void main(String[] args) {

        String encode = encryptPassword("120839");
        System.out.println(encode);
        System.out.println(validatePassword("281814", encode));
    }
}
