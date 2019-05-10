package com.morethan.game.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class RSAUtil{

    //加密方式
    private static final String KEY_ALGORITHM = "RSA";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    //RSA公钥
    private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnrL23/5nMk0A2REFYL6Wpn1Oz1+pHGBL04UBPc9GDj5NPzksWTULKSyqFqydwxMUc08n9XauMPNOqlD8hVXzSyBdA54w1XSoWt9W1IVUDju7VYLcA5BOaOMFkcY8M7FAuePm5Qs9MPCCsxgX5TZSb26zLW/LHhUHNl6sYEiKWBQIDAQAB";

    private static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    //RSA加密数据
    public static String rsaEncrypt(String data) throws Exception{
        //加密后的byte数组
        byte[] encryptDataBytes = encryptByPublicKey(data.getBytes("UTF-8"), PUBLICKEY);
        //经过base64编码
        return Base64.encodeBase64String(encryptDataBytes);
    }

    //MD5加密数据 规则同微信规则，先把参数排序,然后进行拼接，拼接完进行MD5加密
    public static String sign(TreeMap<String, String> treeMap,String securityKeyId) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = treeMap.keySet();
        for(String key : keys) {
            String v = treeMap.get(key);
            if(StringUtils.isNotBlank(v) && !"sign".equals(key)) {
                try {
                    sb.append(key + "=" + URLEncoder.encode(v, "UTF-8") + "&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        sb.append("key=" + securityKeyId);
        String md5 = MD5Util.encode(sb.toString());
        return md5.toUpperCase();
    }

    public static String paramSign(Map<String, Object> param){
        if(param==null || param.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        Set<String> keys = param.keySet();
        for(String key : keys) {
            String v = param.get(key).toString();
            if(StringUtils.isNotBlank(v)) {
                try {
                    if(sb.length()>0) {
                        sb.append("&");
                    }
                    sb.append(key + "=" + URLEncoder.encode(v, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return rsaEncrypt(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
