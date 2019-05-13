package com.morethan.game.interceptor;

import com.morethan.game.common.Constant;
import com.morethan.game.config.EnvConfig;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Score;
import com.morethan.game.service.PlayerService;
import com.morethan.game.service.ScoreService;
import com.morethan.game.utils.RedisUtil;
import com.morethan.game.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *  统一处理header
 *  create by anthony
 */
@EnableConfigurationProperties(EnvConfig.class)
public class ActionCheckInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ScoreService scoreService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        String securityKey = req.getHeader("x-access-key");
        String token = req.getHeader("x-access-token");
        String timestamp = req.getHeader("x-access-timestamp");
        String sign = req.getHeader("x-access-sign");

        //从score表取到未下分的token,根据token反查到用户
        if (StringUtils.isNotEmpty(token)) {
            Score score = scoreService.whichOneByToken(token);
            if (score != null && score.getExitTime()== null && score.getExitAmount() == null) {
                Player player = playerService.whichOne(score.getPlayerId());
                if (null != player) {
                    req.setAttribute("player", player);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
