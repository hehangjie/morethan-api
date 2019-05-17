package com.morethan.game.interceptor;

import com.morethan.game.authorization.TokenCheck;
import com.morethan.game.config.EnvConfig;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Record;
import com.morethan.game.entity.Score;
import com.morethan.game.service.PlayerService;
import com.morethan.game.service.RecordService;
import com.morethan.game.service.ScoreService;
import com.morethan.game.utils.DateUtil;
import com.morethan.game.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by 张良
 */
@Component
@EnableConfigurationProperties(EnvConfig.class)
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RecordService recordService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //注明Authorization注解的方法不校验登录
        if (method.getAnnotation(TokenCheck.class) != null) {

            String token = req.getHeader("x-access-token");

            if (StringUtils.isEmpty(token)) {
                return false;
            }

            //score不存在
            Score score = scoreService.whichOneByToken(token);
            if (score == null) {
                return false;
            }



            Player player = playerService.whichOne(score.getPlayerId());
            //玩家不存在
            if (null == player) {
                return false;
            }
            req.setAttribute("player", player);
            req.setAttribute("score", score);

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
