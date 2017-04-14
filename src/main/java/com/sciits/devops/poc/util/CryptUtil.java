
package com.sciits.devops.poc.util;


import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
//import org.jasypt.util.password.StrongPasswordEncryptor;

public class CryptUtil {

    private static final String MD5 = "MD5";
    private static final String SHA_512 = "SHA-512";
    //private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public static String getShaHash(String payload) throws Exception {
        MessageDigest md = MessageDigest.getInstance(SHA_512);
        byte[] bytes = md.digest(payload.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getMd5Hash(String payload) throws Exception {
        MessageDigest mda = MessageDigest.getInstance(MD5);
        byte[] digesta = mda.digest(payload.getBytes());

        return Hex.encodeHex(digesta).toString();
    }

   /* public static String getEncyptedPassword(UserDetails user) {
        if (user.getUser_auth_details() == null || StringUtils.isEmpty(user.getUser_auth_details().getEncrPwd())) {
            return null;
        }
        return passwordEncryptor.encryptPassword(user.getUser_auth_details().getEncrPwd());
    }*/

//    public static String getEncyptedPassword(WebUser user) {
//        if (user.getPwd() == null || StringUtils.isEmpty(user.getPwd().getEncrPwd())) {
//            return null;
//        }
//        return passwordEncryptor.encryptPassword(user.getPwd().getEncrPwd());
//    }
}
