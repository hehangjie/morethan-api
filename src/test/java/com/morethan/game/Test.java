package com.morethan.game;

import com.morethan.game.utils.DateUtil;

/**
 * 描述:
 * test
 *
 * @outhor anthony
 * @create 2018-11-15 下午2:17
 */
public class Test {

    public static void main(String[] args) {
        long time1 = DateUtil.getTimestamp()/1000;
        try{
            Thread.sleep(3000);
        }catch (Exception ex) {

        }

        long time2 = DateUtil.getTimestamp()/1000;
        System.out.println(time2 - time1);
        System.out.println(1542370538722l-1542370524243l);

    }

}

