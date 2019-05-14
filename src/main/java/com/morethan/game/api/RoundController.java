package com.morethan.game.api;

import com.alibaba.fastjson.JSONObject;
import com.morethan.game.authorization.UnAuthorization;
import com.morethan.game.dto.Bet;
import com.morethan.game.dto.RequestBet;
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
import com.morethan.game.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @UnAuthorization
    @PostMapping("new")
    @ApiOperation(value = "新游戏开局")
    @Transactional
    public Result<Map> newRound(HttpServletRequest request, @RequestBody RequestBet bet) {

        Player player = (Player) request.getAttribute("player");
        Score score = (Score) request.getAttribute("score");

        if (player == null) {
            return Result.fail("无效token");
        }

        if (bet.getGameId() != 10777) {
            return Result.fail("无效的游戏ID");
        }

        if (bet.getBetList().size() < 1) {
            return Result.fail("用户没有下注");
        }

        double totalBet = 0.0;
        for (Bet b : bet.getBetList()) {
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

        Map<String, Object> resultMap = new HashedMap();
        try {

            //开奖
            Lottery lottery = sevenService.deal(false, bet.getBetList());

            //插入记录
            Record record = new Record();
            record.setPlayerId(player.getPlayerId());
            record.setBeginTime(DateUtil.getCurrentTime());
            record.setAmount(lottery.getAmount());
            record.setBet(JSONObject.toJSONString(bet.getBetList()));
            record.setScoreId(score.getScoreId());
            record.setDominate(false);
            recordService.insert(record);
            resultMap.put("lottery", lottery);

            //刷新用户余额
            player.setExperience(BigDecimal.valueOf(player.getExperience()).add(BigDecimal.valueOf(lottery.getAmount())).doubleValue());
            boolean isUpdate = playerService.updateById(player);
            if(!isUpdate){
                throw new Exception("乐观锁更新失败");
            }
            resultMap.put("experience", player.getExperience());

        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return Result.ok(resultMap);
    }

    @UnAuthorization
    @PostMapping("record")
    @ApiOperation(value = "游戏结算")
    public Result<Map> recordRound(HttpServletRequest request, @RequestBody RequestBet bet) {
        return Result.ok();
    }


}
