package com.morethan.game.service.game;

import com.morethan.game.dto.Lottery;
import com.morethan.game.utils.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-10 下午2:09
 */
@Service
public class SevenService {

    private static final List<String> items = Arrays.asList(
            "0:orange","1:bell","2:bar-s","3:bar","4:apple","5:apple-s","6:mango", //上排
            "7:watermelon","8:watermelon-s","9:lucky-s","10:apple","11:orange-s","12:orange", //右排
            "13:bell","14:seven-s","15:seven","16:apple","17:mango-s","18:mango", //下排
            "19:star-s","20:star","21:lucky","22:apple","23:bell-s"//左排
    );

    private static final Map<String, Integer> itemsProbability = new HashMap<String, Integer>() {{
        put("0:orange", 18 );put("1:bell", 27);put("2:bar-s", 34);
        put("3:bar", 37);put("4:apple", 55);put("5:apple-s", 72);
        put("6:mango", 84);put("7:watermelon", 102);put("8:watermelon-s", 119);
        put("9:lucky-s", 137);put("10:apple", 155);put("11:orange-s", 172);
        put("12:orange", 190);put("13:bell", 199);put("14:seven-s", 216);
        put("15:seven", 225);put("16:apple", 243);put("17:mango-s", 260);
        put("18:mango", 272);put("19:star-s", 289);put("20:star", 301);
        put("21:lucky", 319);put("22:apple", 337);put("23:bell-s", 354);
    }};

    public Lottery deal() {
        Lottery lottery = new Lottery();
        int r = RandomUtil.rollInt(1, 354);
        for (int i = items.size() - 1; i >= 0; i--) {

            lottery.setTitle(items.get(i).split(":")[1]);
            lottery.setType(lottery.getTitle());
            lottery.setItems(new String[]{items.get(i)});

            if (i == 0) {//最小的一个是桔子
                return lottery;
            }

            if (itemsProbability.get(items.get(i)) >= r && itemsProbability.get(items.get(i - 1)) < r) {

                if (items.get(i).equals("9:lucky-s")) {
                    lottery.setType("小三元");
                    lottery.setItems(new String[]{"0:orange", "6:mango", "13:bell"});
                } else if (items.get(i).equals("21:luck")) {
                    lottery.setType("大三元");
                    lottery.setItems(new String[]{"8:watermelon", "20:star", "15:seven"});
                }

                return lottery;
            }
        }

        return null;
    }

//    public static void main(String[] args) {
//        for(int i=0;i<20;i++) {
//            Lottery lottery = new SevenService().deal();
//            System.out.println(lottery.getTitle()+":"+lottery.getType());
//        }
//
//    }


}

