package com.morethan.game.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * MD5加密工具
 */
public class MD5Util {

    private static final int TIMEOUT = 150000;

    public static String encode(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 签名算法
     *
     * @param treeMap
     * @return
     */
    private static String sign(Map<String, String> treeMap) {
        StringBuffer buff = new StringBuffer("secret=C005FB711C1E5BB359AF44CFB506F73B;");
        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            buff.append(key + "=" + treeMap.get(key) + ";");
        }
        String md5 = MD5Util.encode(buff.toString());
        return md5;
    }

    /**
     * 签名验证
     *
     * @param treeMap
     * @return
     */
    public static boolean checkSign(Map<String, String> treeMap, String reqSign) {
        String sign = sign(treeMap);
        if (sign.equals(reqSign)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Map<String, String> treeMap = new HashMap<>();
        treeMap.put("title","hello");
        treeMap.put("amount","40");
        String sign = MD5Util.sign(treeMap);
        System.out.println(sign);
    }

}