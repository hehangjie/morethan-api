package com.morethan.game.utils;

import java.util.Random;

/**
 * RandomUtil
 *
 * @Description: RandomUtil
 * @Author: 伯符
 * @CreateDate: 2018/11/11
 * @Version: 1.0
 */
public class RandomUtil {


    public static int rollInt(int min, int max) {
        if (min > max) return max;
        //if(max- min < min) return min;

        Random r = new Random();
        return r.nextInt(max+1-min) + min;
    }


    public static double rollDouble(double min, double max) {

        int minInt = (int) min * 100;
        int maxInt = (int) max * 100;

        int r = rollInt(minInt, maxInt);

        return ((double) r) / 100;
    }

    public static void main(String[] args) {
        double a = RandomUtil.rollDouble(0.01, 0.01);
        double b = RandomUtil.rollDouble(0.01, 2.00);
        System.out.println(a);
        System.out.println(b);

        int min = 0, max = 10;
        for (int i = 0; i < 100; i++) {
            int r = rollInt(min, max);
            if (r >= max || r<=min) {
                System.out.print("'" + r + "',");
            }

        }
    }
}
