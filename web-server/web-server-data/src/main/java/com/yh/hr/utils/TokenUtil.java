package com.yh.hr.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.util.Base64;

public class TokenUtil {

    private static final String SEPARATOR = "-";


    public static String createToken(String auth) {
        StringBuilder builder = new StringBuilder(auth);
        return builder.append(SEPARATOR).append(System.currentTimeMillis()).append(SEPARATOR).append(RandomStringUtils.random(10, true, true).toUpperCase()).toString();
    }

    public static String getOpenIdFromToken(String token) {

        try {
            String decodedStr = getAuthFromToken(token);
            return decodedStr.split(":")[1];

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String getUserCodeFromToken(String token) {

        try {
            String decodedStr = getAuthFromToken(token);
            return decodedStr.split(":")[0];

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String getAuthFromToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String[] param = token.split(SEPARATOR);
        if (param.length <= 3) {
            return null;
        }
        try {
            String oauth = param[param.length - 1];
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedByteArr = decoder.decode(oauth);
            String decodedStr = new String(decodedByteArr);
            return decodedStr;

        } catch (NumberFormatException e) {
            return null;
        }
    }
}
