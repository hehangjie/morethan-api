package com.morethan.game.utils;

import org.springframework.util.StringUtils;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-07 下午4:56
 */
public class TokenUtil {

    public static String getToken(Long id) {
        int r = RandomUtil.rollInt(1, 9);
        long timeId = 1000 * r + id;
        return r + Long.toHexString(timeId);
    }

    public static Long reverseToken(String token) {
        if (StringUtils.isEmpty(token) || token.length() < 1) {
            return 0L;
        }

        try {
            int r = Integer.parseInt(token.substring(0, 1));
            Long id = Long.parseLong(token.substring(1, token.length()), 16) - 1000 * r;
            return id;
        } catch (NumberFormatException ex) {
            System.err.println(ex);
            return 0L;
        }
    }

    public static void main(String[] args) {
        String token = TokenUtil.getToken(1L);
        System.out.println(token);
        System.out.println(TokenUtil.reverseToken(token));
    }

}

