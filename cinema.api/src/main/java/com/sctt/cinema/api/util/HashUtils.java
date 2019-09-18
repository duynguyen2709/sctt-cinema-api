package com.sctt.cinema.api.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String hashSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest    = MessageDigest.getInstance("SHA-256");
        byte[]        shaByteArr = mDigest.digest(encodeUTF8(input));
        StringBuilder hexString  = new StringBuilder();

        for(int i = 0; i < shaByteArr.length; ++i) {
            String hex = Integer.toHexString(255 & shaByteArr[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    private static byte[] encodeUTF8(String string) {
        return string.getBytes(Charset.forName("UTF-8"));
    }
}
