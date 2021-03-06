package com.morethan.game.api;

import com.morethan.game.authorization.TokenCheck;
import com.morethan.game.dto.Bet;
import com.morethan.game.dto.Lottery;
import com.morethan.game.dto.Result;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Record;
import com.morethan.game.entity.Score;
import com.morethan.game.service.PlayerService;
import com.morethan.game.service.RecordService;
import com.morethan.game.service.ScoreService;
import com.morethan.game.service.game.SevenService;
import com.morethan.game.utils.DateUtil;
import com.morethan.game.utils.DominateUtil;
import com.morethan.game.utils.RandomUtil;
import com.morethan.game.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DemoController
 *
 * @Description: DemoController
 * @Author: 伯符
 * @CreateDate: 2018/11/10
 * @Version: 1.0
 */


@RestController
@RequestMapping("/round")
public class RoundController {

    private final Logger logger = LoggerFactory.getLogger(RoundController.class);

    @Autowired
    private PlayerService playerService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SevenService sevenService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private ScoreService scoreService;

    @TokenCheck
    @PostMapping("new")
    @ApiOperation(value = "新游戏开局")
    public Result<Map> newRound(HttpServletRequest request, @RequestParam Integer gameId, @RequestParam String bets) {
        //[{"title":"apple","amount":0.8},{"title":"seven","amount":0.2}]
        //[{"title":"big","amount":1.4}]
        Player player = (Player) request.getAttribute("player");
        Score score = (Score) request.getAttribute("score");

        //score已下分
        if (score.getExitTime() != null || score.getExitAmount() != null) {
            return Result.fail("Token失效");
        }

        Record record = recordService.selectById(score.getLastRecordId());
        if (record != null) {
            long timeDifference = DateUtil.difference(record.getRecordTime());
            //和上一局间隔低于5秒
            if (timeDifference < 5) {
                return Result.fail("请求太频繁");
            }
        }

        List<Bet> betList = new ArrayList<>();
        try {
            JSONArray array = JSONArray.fromObject(bets);
            betList = JSONArray.toList(array, new Bet(), new JsonConfig());
        } catch (Exception ex) {
            return Result.fail("bet对象转换错误");
        }

        if (gameId != 10777) {
            return Result.fail("无效的游戏ID");
        }

        if (betList.size() < 1) {
            return Result.fail("用户没有下注");
        }

        double totalBet = 0.0;
        Map<String, Object> resultMap = new HashedMap();
        double luckPool = RandomUtil.rollDouble(990000,1020000);
        boolean dominate = false;
        Lottery lottery = new Lottery();
        //开大小
        if (betList.size() == 1 && (betList.get(0).getTitle().equals("big") || betList.get(0).getTitle().equals("small"))) {
            Bet bet = betList.get(0);
            if (bet.getTitle().equals("big") || bet.getTitle().equals("small")) {

                Record lastRecord = recordService.selectById(score.getLastRecordId());
                if (lastRecord == null || lastRecord.getAmount() + lastRecord.getBetAmount() < bet.getAmount() ) {
                    return Result.fail("无效投注");
                }

                lottery.setBetAmount(bet.getAmount());
                int r = RandomUtil.rollInt(1, 14);
                lottery.setItems(new Integer[] {r});
                if(r< 8) {//开小
                    if(bet.getTitle().equals("big")){
                        lottery.setTitle("small");
                        lottery.setAmount(-bet.getAmount());
                        lottery.setLotteryAmount(0.0);
                    }else{
                        lottery.setTitle(bet.getTitle());
                        lottery.setAmount(bet.getAmount());
                        lottery.setLotteryAmount(bet.getAmount()*2);
                    }
                }else{//开大
                    if(bet.getTitle().equals("small")){
                        lottery.setTitle("big");
                        lottery.setAmount(-bet.getAmount());
                        lottery.setLotteryAmount(0.0);
                    }else{
                        lottery.setTitle(bet.getTitle());
                        lottery.setAmount(bet.getAmount());
                        lottery.setLotteryAmount(bet.getAmount()*2);
                    }
                }

            }

            resultMap = recordService.record(lottery, player, bets, score, dominate);
            resultMap.put("luckPool", luckPool + lottery.getAmount());
            return Result.ok(resultMap);
        }

        for (Bet b : betList) {
            if (b.getAmount() < 0.1) {
                return Result.fail("无效投注额:" + b.getAmount());
            }
            if (!b.getTitle().equals("apple") && !b.getTitle().equals("orange") && !b.getTitle().equals("mango")
                    && !b.getTitle().equals("bell") && !b.getTitle().equals("watermelon") && !b.getTitle().equals("star")
                    && !b.getTitle().equals("seven") && !b.getTitle().equals("bar")) {
                return Result.fail("无效投注:" + b.getTitle());
            }
            totalBet += b.getAmount();
        }

        if (totalBet > player.getExperience()) {
            return Result.fail("无效投注，Experience less than bet");
        }

        //GameEntry
        if (score.getGameEntryTime() == null) {
            score.setGameEntryTime(DateUtil.getCurrentTime());
            scoreService.updateById(score);
        }

//        Double roundAmount = recordService.sumRecordByScore(score.getScoreId());
//
//        if (roundAmount > 1000) {
//            dominate = DominateUtil.threeQuartersDominate();
//        } else if (roundAmount < -200) {
//            dominate = false;
//        } else {
//            dominate = DominateUtil.halfDominate();
//        }
        dominate = RandomUtil.rollInt(1,10)>7?true:false;

        //开奖
        lottery = sevenService.loopDeal(dominate, betList);
        resultMap = recordService.record(lottery, player, bets, score, dominate);
        resultMap.put("luckPool", luckPool + lottery.getAmount());
        return Result.ok(resultMap);
    }


}
