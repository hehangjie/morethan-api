package com.morethan.game.utils;

public class DominateUtil {

    public static boolean halfDominate(){
        return RandomUtil.rollInt(0,9)>4?true:false;
    }

    public static boolean threeQuartersDominate(){
        return RandomUtil.rollInt(0,11)>2?true:false;
    }


    public static void main(String[] args){
        for(int i=0; i<20; i++) {
            System.out.println(DominateUtil.halfDominate());
        }
    }
}
