package com.morethan.game.api;

import com.morethan.game.authorization.TokenCheck;
import com.morethan.game.dto.Result;
import com.morethan.game.entity.Player;
import com.morethan.game.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/game")
public class GameController {

    @Autowired
    private ScoreService scoreService;

    @TokenCheck
    @GetMapping("quit")
    @ApiOperation(value = "退出游戏")
    public Result<Map> quitGame(HttpServletRequest request) {
        Player player = (Player) request.getAttribute("player");
        boolean b = scoreService.exitScore(player);
        if(!b){
            return Result.fail();
        }

        return Result.ok();

    }

    public Result<Map> entryScore(@RequestParam String udid, @RequestParam Double amount){
        //scoreService.entryScore()
        return Result.ok();
    }


}
