package com.morethan.game.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
        StringBuffer buff = new StringBuffer();
        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if("sign".equals(key)) continue;
            buff.append(key + "=" + treeMap.get(key) + ";");
        }
        String md5 = MD5Util.encode(buff.toString());
        return md5;
    }

    public static String sign(Map<String, String> treeMap,String secret) {
    	StringBuilder sb = new StringBuilder();
        Set<String> keys = treeMap.keySet();
        for(String key : keys) {
        	String v = treeMap.get(key);
        	if(StringUtils.isNotBlank(v) && !"sign".equals(key) && !"key".equals(key)) {
                try {
					sb.append(key + "=" + URLEncoder.encode(v, "UTF-8") + "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
        }
        sb.append("key=" + secret);
        String md5 = encode(sb.toString());
        return md5.toUpperCase();
    }
    /**
     * 签名验证
     *
     * @param treeMap
     * @return
     */
    public static boolean checkSign(Map<String, String> treeMap) {
        long currentTime = DateUtil.getTimestamp();
        long requestTime = Long.parseLong(treeMap.get("timestamp")==null?"0":treeMap.get("timestamp"));
        if (Math.abs(currentTime-requestTime)>TIMEOUT) {
            return false;
        }
        String sign = sign(treeMap);
        if (sign.equals(treeMap.get("sign"))) {
            return true;
        }
        return false;
    }

}