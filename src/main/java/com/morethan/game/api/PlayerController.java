package com.morethan.game.api;

import com.morethan.game.authorization.UnAuthorization;
import com.morethan.game.common.Constant;
import com.morethan.game.entity.Player;
import com.morethan.game.service.PlayerService;
import com.morethan.game.dto.Result;
import com.morethan.game.utils.MD5Util;
import com.morethan.game.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * DemoController
 *
 * @Description: DemoController
 * @Author: 伯符
 * @CreateDate: 2018/11/10
 * @Version: 1.0
 */


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private RedisUtil redisUtil;

    @UnAuthorization
    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public Result<Map> info(HttpServletRequest request) {
        Player player = (Player) request.getAttribute("player");
        if(player == null) {
            return Result.fail("无效token");
        }
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("player", player);
        return Result.ok(resultMap);
    }

}
