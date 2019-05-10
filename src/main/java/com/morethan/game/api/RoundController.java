package com.morethan.game.api;

import com.morethan.game.authorization.UnAuthorization;
import com.morethan.game.dto.Bet;
import com.morethan.game.dto.Lottery;
import com.morethan.game.dto.Result;
import com.morethan.game.entity.Player;
import com.morethan.game.service.PlayerService;
import com.morethan.game.service.game.SevenService;
import com.morethan.game.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PlayerService playerService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SevenService sevenService;

    @UnAuthorization
    @GetMapping("new")
    @ApiOperation(value = "新游戏开局")
    public Result<Map> newRound(HttpServletRequest request, @RequestBody Bet bet) {

        Map<String, Object> resultMap = new HashedMap();
        Lottery lottery = sevenService.deal();
        resultMap.put("lottery", lottery);
        return Result.ok(resultMap);
    }

    @UnAuthorization
    @PostMapping("record")
    @ApiOperation(value = "游戏结算")
    public Result<Map> recordRound(HttpServletRequest request, @RequestBody Bet bet) {
        return Result.ok();
    }


}
