package com.morethan.game.service.game;

import com.morethan.game.dto.Bet;
import com.morethan.game.dto.Lottery;
import com.morethan.game.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-10 下午2:09
 */
@Service
public class SevenService {

    private final Logger logger = LoggerFactory.getLogger(SevenService.class);

    //奖项表
    private static final List<String> items = Arrays.asList(
            //"0-orange-10", "1-bell-20", "2-bar-s-50", "3-bar-120", "4-apple-5", "5-apple-s-3", "6-mango-15"
            "orange", "bell", "bar-s", "bar", "apple", "apple-s", "mango", //上排
            //"7-watermelon-20", "8-watermelon-s-3", "9-lucky-s-0", "10-apple-5", "11-orange-s-3", "12-orange-10"
            "watermelon", "watermelon-s", "lucky-s", "apple", "orange-s", "orange", //右排
            //"13-bell-20", "14-seven-s-3", "15-seven-40", "16-apple-5", "17-mango-s-3", "18-mango-15"
            "bell", "seven-s", "seven", "apple", "mango-s", "mango", //下排
            //"19-star-s-3", "20-star-30", "21-lucky-0", "22-apple-5", "23-bell-s-3"
            "star-s", "star", "lucky", "apple", "bell-s",//左排
            //"24-empty-0"
            "empty"
    );

    //倍率表
    private static final List<Integer> itemsOdds = Arrays.asList(
            //"0-orange-10", "1-bell-20", "2-bar-s-50", "3-bar-120", "4-apple-5", "5-apple-s-3", "6-mango-15"
            10, 20, 50, 120, 5, 3, 15,
            //"7-watermelon-20", "8-watermelon-s-3", "9-lucky-s-0", "10-apple-5", "11-orange-s-3", "12-orange-10"
            20, 3, 0, 5, 3, 10,
            //"13-bell-20", "14-seven-s-3", "15-seven-40", "16-apple-5", "17-mango-s-3", "18-mango-15"
            20, 3, 40, 5, 3, 15,
            //"19-star-s-3", "20-star-30", "21-lucky-0", "22-apple-5", "23-bell-s-3"
            3, 30, 0, 5, 3,
            //"24-empty-0"
            0
    );

    //概率表
    private static final List<Integer> itemsProbability = Arrays.asList(
            //"0-orange-10", "1-bell-20", "2-bar-s-50", "3-bar-120", "4-apple-5", "5-apple-s-3", "6-mango-15"
            18, 27, 34, 37, 55, 72, 84,
            //"7-watermelon-20", "8-watermelon-s-3", "9-lucky-s-0", "10-apple-5", "11-orange-s-3", "12-orange-10"
            102, 119, 137, 155, 172, 190,
            //"13-bell-20", "14-seven-s-3", "15-seven-40", "16-apple-5", "17-mango-s-3", "18-mango-15"
            199, 216, 225, 243, 260, 272,
            //"19-star-s-3", "20-star-30", "21-lucky-0", "22-apple-5", "23-bell-s-3"
            289, 301, 319, 337, 354
    );

    public Lottery loopDeal(boolean angry, List<Bet> bets) {
        Lottery lottery = deal(angry, bets);
        while (lottery == null) {
            lottery = deal(angry, bets);
        }

        return lottery;
    }

    public Lottery deal(boolean angry, List<Bet> bets) {

        Double[] count = new Double[]{0.0,0.0,0.0};
        Lottery lottery = new Lottery();
        lottery.setTitle(items.get(0));
        lottery.setType(lottery.getTitle());
        lottery.setItems(new Integer[]{0});

        int r = RandomUtil.rollInt(0, 354);
        for (int i = items.size() - 2; i > 0; i--) {

            if (itemsProbability.get(i) >= r && itemsProbability.get(i - 1) < r) {

                lottery.setTitle(items.get(i));
                lottery.setType(lottery.getTitle());
                lottery.setItems(new Integer[]{i});

                if (i == 9 || i == 21) {//小lucky
                    lottery = dealLucky(i);
                }

                break;
            }
        }

        try {
            count = compute(bets, lottery.getItems());
        }catch (NullPointerException ex){
            logger.error(ex.getStackTrace().toString());
        }
        lottery.setBetAmount(BigDecimal.valueOf(count[0]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        lottery.setLotteryAmount(BigDecimal.valueOf(count[1]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        lottery.setAmount(BigDecimal.valueOf(count[2]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        if (!angry) {
            return lottery;
        } else {
            if (lottery.getAmount() > 0.0) {
                return null;
            }
            return lottery;
        }
    }

    /**
     * 计算亏盈结果
     *
     * @param bets
     * @param lotteryItems
     * @return
     */
    public Double[] compute(List<Bet> bets, Integer[] lotteryItems) {

        double betAmount = 0.0;
        double lotteryAmount = 0.0;
        for (Bet b : bets) {

            if (b.getTitle().equals("apple")) {
                for (int l : lotteryItems) {
                    if (l == 4 || l == 5 || l == 10 || l == 16 || l == 22)
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("orange")) {
                for (int l : lotteryItems) {
                    if (l == 0 || l == 11 || l == 12 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("mango")) {
                for (int l : lotteryItems) {
                    if (l == 6 || l == 17 || l == 18 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("bell")) {
                for (int l : lotteryItems) {
                    if (l == 1 || l == 13 || l == 23 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("watermelon")) {
                for (int l : lotteryItems) {
                    if (l == 7 || l == 8 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("star")) {
                for (int l : lotteryItems) {
                    if (l == 19 || l == 20 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("seven")) {
                for (int l : lotteryItems) {
                    if (l == 14 || l == 15 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }else if (b.getTitle().equals("bar")) {
                for (int l : lotteryItems) {
                    if (l == 2 || l == 3 )
                        lotteryAmount += b.getAmount() * itemsOdds.get(l);
                }
            }

            betAmount += b.getAmount();
        }

        return new Double[]{betAmount, lotteryAmount, lotteryAmount - betAmount};
    }

    public Lottery dealLucky(int i) {
        Lottery lottery = new Lottery();
        lottery.setTitle(items.get(i));
        int r = RandomUtil.rollInt(0, 4);
        if (r == 0) {
            lottery.setType("真可惜");
            lottery.setItems(new Integer[]{25});
        } else if (r == 1) {
            lottery.setType("小三元");
            lottery.setItems(new Integer[]{0, 6, 13});
        } else if (r == 2) {
            lottery.setType("大三元");
            lottery.setItems(new Integer[]{8, 15, 20});
        } else if (r == 3) {
            lottery.setType("大四喜");
            lottery.setItems(new Integer[]{4, 10, 16, 22});
        } else if (r == 4) {
            if (i == 21) {
                lottery.setType("大火车");
                r = RandomUtil.rollInt(0, 20);
                lottery.setItems(new Integer[]{r, r - 1, r - 2, r - 3});
            } else {
                lottery.setType("小火车");
                r = RandomUtil.rollInt(0, 20);
                lottery.setItems(new Integer[]{r, r - 1, r - 2});
            }
        }
        return lottery;
    }

//    public static void main(String[] args) {
//        for(int i=0;i<20;i++) {
//            Lottery lottery = new SevenService().deal();
//            System.out.println(lottery.getTitle()+":"+lottery.getType());
//        }
//
//    }


}

