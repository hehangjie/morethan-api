package com.morethan.game.api;

import com.morethan.game.authorization.UnAuthorization;
import com.morethan.game.config.EnvConfig;
import com.morethan.game.dto.Result;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Score;
import com.morethan.game.service.PlayerService;
import com.morethan.game.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
@RequestMapping("/")
@EnableConfigurationProperties(EnvConfig.class)
public class EnterController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PlayerService playerService;

    @UnAuthorization
    @GetMapping("/")
    @ApiOperation(value = "默认页")
    public Result<Map> quitGame(HttpServletRequest request) {
        if(EnvConfig.getDev()){
            List<Score> noEnterGame = scoreService.listNoGameEntry();
            if(noEnterGame.size() < 1) {
                List<Player> players = playerService.playersNotInGame();
                for (Player player : players) {
                    scoreService.entryScore(player.getPlayerId(), player.getExperience());
                }
                noEnterGame = scoreService.listNoGameEntry();
            }

            //查出所有未下分的token
            List<Score> noExit = scoreService.listNoExit();
            Map<String, Object> result = new HashMap<>();
            result.put("enableToken", noEnterGame);
            result.put("isGaming", noExit);
            return Result.ok(result);
        }
        return Result.ok();
    }

}
