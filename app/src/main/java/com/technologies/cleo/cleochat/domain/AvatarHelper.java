package com.technologies.cleo.cleochat.domain;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pepe on 10/21/16.
 */

public class AvatarHelper {

    public final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static String getAvatarUrl(String email) {
        Log.e("AvatarHelper", GRAVATAR_URL + md5(email) + "?s=72");
        return GRAVATAR_URL + md5(email) + "?s=72";
    }

    private static final String md5(final String s) {
        final String md5 = "MD5";
        try {
            //Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(md5);
            digest.update(s.getBytes());
            byte MessageDigest[] = digest.digest();

            //Create HEX string
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : MessageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
